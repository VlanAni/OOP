package vanisimov.hashtable.elements;

public class Node<K, V> implements Entry<K, V> {

    private K key;
    private V value;
    private Node<K, V> next;

    Node (K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    void setValue (V newValue) {
        this.value = newValue;
    }

    void setNextNode (Node<K, V> newNext) {
        this.next = newNext;
    }

    public V getValue() {
        return this.value;
    }

    public K getKey() {
        return this.key;
    }

    Node<K, V> getNext() {
        return this.next;
    }

}