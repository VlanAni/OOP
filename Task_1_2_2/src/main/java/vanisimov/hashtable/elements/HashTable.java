package vanisimov.hashtable.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import vanisimov.hashtable.SystemIO.StdOut;

public class HashTable<K, V> implements Iterable<Entry<K, V>> {

    private static final int stdSize = 10000; // standard capacity for new HashTable
    private static final double maxLoadCoef = 0.85;
    private ArrayList<Node<K, V>> table;
    private ArrayList<K> keys;
    private int size;
    private int recordsAmount;
    private int modAmount;


    public HashTable() {
        this.size = HashTable.stdSize;
        this.table = new ArrayList<Node<K, V>>(this.size);
        this.keys = new ArrayList<K>();
        for (int i = 0; i < this.size; ++i) {
            this.table.add(null);
        }
        this.recordsAmount = 0;
        this.modAmount = 0;
    }

    public V getValue(K key) {
        if (key == null) {
            return null;
        }
        int hashIdx = this.hash(key);
        Node<K, V> node = this.table.get(hashIdx);
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            node = node.getNext();
        }
        return null;
    }

    // true - value updated, otherwise - false
    public boolean updValue(K key, V newValue) {
        if (key == null || newValue == null) {
            return false;
        }
        int hashIdx = this.hash(key);
        Node<K, V> node = this.table.get(hashIdx);
        while (node != null) {
            if (node.getKey().equals(key)) {
                node.setValue(newValue);
                this.modAmount++;
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    public boolean checkValue(K key) {
        if (key == null) {
            return false;
        }
        int hashIdx = this.hash(key);
        Node<K, V> node = this.table.get(hashIdx);
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue() != null;
            }
            node = node.getNext();
        }
        return false;
    }

    // true - if wee removed this element, false - there is no such a key in the table
    public boolean remove(K key) {
        if (key == null) {
            return false;
        }
        int hashIdx = this.hash(key);
        Node<K, V> node = this.table.get(hashIdx);
        int nodeNum = 0;
        Node<K, V> lastNode = node;
        while (node != null) {
            nodeNum++;
            if (node.getKey().equals(key)) {
                if (nodeNum == 1) {
                    this.table.set(hashIdx, node.getNext());
                } else {
                    lastNode.setNextNode(node.getNext());
                }
                this.keys.remove(key);
                this.recordsAmount--;
                this.modAmount++;
                return true;
            }
            lastNode = node;
            node = node.getNext();
        }
        return false;
    }

    // true - if new record has been addrd, otherwise - false
    public boolean add(K key, V value) {
        if (key == null || value == null) {
            return false;
        }
        boolean isExists = this.checkValue(key);
        // if the record with such a key exists, do nothing
        if (!isExists) {
            int newRecAmount = this.recordsAmount + 1;
            double loadCoef = (double) newRecAmount / (double) this.size;
            if (loadCoef > HashTable.maxLoadCoef) {
                this.resizeTable();
            }
            this.addNodeToBucket(key, value);
            this.recordsAmount++;
            this.keys.add(key);
            this.modAmount++;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            try {
                // Прямое преобразование к HashTable<K, V>
                @SuppressWarnings("unchecked")
                HashTable<K, V> other = (HashTable<K, V>) obj;

                if (this.recordsAmount != other.recordsAmount) {
                    return false;
                }

                for (K key : this.keys) {
                    V thisValue = this.getValue(key);
                    V otherValue = other.getValue(key);
                    if (otherValue == null || !thisValue.equals(otherValue)) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public void printTable() {
        StdOut.println("==HASH TABLE==");
        for (K key : this.keys) {
            StdOut.print("[Key: ");
            StdOut.print(key);
            StdOut.print(" | ");
            StdOut.print("Value: ");
            StdOut.print(this.getValue(key));
            StdOut.println(" ]");
        }
        StdOut.println("====");
    }

    public int getRecordsAmount() {
        return this.recordsAmount;
    }

    private int hash(K key) {
        int keyHash = key.hashCode();
        return (keyHash & 0x7fffffff) % this.size;
    }

    private void resizeTable() {
        this.modAmount++;
        int oldSize = this.size;
        this.size *= 2;
        ArrayList<Node<K, V>> newArr = new ArrayList<Node<K, V>>(this.size);
        for (int i = 0; i < this.size; ++i) {
            newArr.add(null);
        }
        ArrayList<Node<K, V>> oldArr = this.table;
        this.table = newArr;
        for (int i = 0; i < oldSize; ++i) {
            Node<K, V> node = oldArr.get(i);
            while (node != null) {
                this.addNodeToBucket(node.getKey(), node.getValue());
                node = node.getNext();
            }
        }
    }

    private void addNodeToBucket(K key, V value) {
        int hashIdx = this.hash(key);
        Node<K, V> newNode = new Node<K, V>(key, value);
        newNode.setNextNode(this.table.get(hashIdx));
        this.table.set(hashIdx, newNode);
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<Entry<K, V>> {

        private int expectedModCount;
        private int currentBucket;
        private Node<K, V> currentNode;
        private Node<K, V> lastReturned;

        HashTableIterator() {
            this.expectedModCount = HashTable.this.modAmount;
            this.currentBucket = 0;
            this.currentNode = null;
            this.lastReturned = null;
            this.advanceSearch();
        }

        private void checkForComodification() {
            if (HashTable.this.modAmount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void advanceSearch() {
            if (this.currentNode != null) {
                this.currentNode = this.currentNode.getNext();
            }

            while (this.currentNode == null && this.currentBucket < HashTable.this.size) {
                this.currentNode = HashTable.this.table.get(this.currentBucket);
                this.currentBucket++;
            }
        }

        @Override
        public boolean hasNext() {
            this.checkForComodification();
            return this.currentNode != null;
        }

        @Override
        public Entry<K, V> next() {
            this.checkForComodification();

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node<K, V> toReturn = this.currentNode;
            this.lastReturned = toReturn;
            this.advanceSearch();
            return toReturn;
        }

        @Override
        public void remove() {
            checkForComodification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            K keyToRemove = this.lastReturned.getKey();
            HashTable.this.remove(keyToRemove);
            this.expectedModCount = HashTable.this.modAmount;
            lastReturned = null;
        }
    }
}