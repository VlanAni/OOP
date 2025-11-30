package vanisimov.hashtable.elements;

import lombok.Getter;
import lombok.Setter;

public class Node<K, V> implements Entry<K, V> {

    @Getter @Setter private K key;
    @Getter @Setter private V value;
    @Getter @Setter private Node<K, V> next;

    Node (K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}