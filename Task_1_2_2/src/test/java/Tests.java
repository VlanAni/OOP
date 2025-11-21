import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import vanisimov.hashtable.SystemIO.StdOut;
import vanisimov.hashtable.elements.*;

public class Tests {

    private HashTable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void testAddAndGet() {

        assertTrue(table.put("key1", 100));

        assertEquals(100, table.get("key1"));
    }

    @Test
    void testAddDuplicate() {
        table.put("key1", 100);

        assertFalse(table.put("key1", 200));

        assertEquals(100, table.get("key1"));
    }

    @Test
    void testUpdateValue() {
        table.put("key1", 100);

        assertTrue(table.updValue("key1", 200));

        assertEquals(200, table.get("key1"));
    }

    @Test
    void testUpdateNonExistent() {
        assertFalse(table.updValue("nonexistent", 100));
    }

    @Test
    void testCheckValue() {
        table.put("key1", 100);

        assertTrue(table.containsKey("key1"));

        assertFalse(table.containsKey("nonexistent"));
    }

    @Test
    void testRemove() {
        table.put("key1", 100);

        assertTrue(table.remove("key1"));

        assertFalse(table.containsKey("key1"));

        assertEquals(0, table.size());
    }

    @Test
    void testRemoveNonExistent() {
        assertFalse(table.remove("nonexistent"));
    }

    @Test
    void testNullHandling() {

        assertFalse(table.put(null, 100));

        assertFalse(table.put("key", null));

        assertNull(table.get(null));

        assertFalse(table.updValue(null, 100));

        assertFalse(table.containsKey(null));

        assertFalse(table.remove(null));

    }

    @Test
    void testResize() {

        for (int i = 0; i < 9000; i++) {
            table.put("key" + i, i);
        }

        assertEquals(8999, table.get("key8999"));
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.put("key1", 100);
        table1.put("key2", 200);

        table2.put("key1", 100);
        table2.put("key2", 200);

        assertEquals(table1, table2);
    }

    @Test
    void testNotEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.put("key1", 100);
        table2.put("key1", 200);

        assertNotEquals(table1, table2);
    }

    @Test
    void testPrintTable() {
        table.put("key1", 100);
        table.put("key2", 200);

        assertDoesNotThrow(() -> StdOut.print(table));
    }

    private void fillTableForIteration() {
        table.put("apple", 10);
        table.put("banana", 20);
        table.put("potatoes", 30);
    }

    @Test
    void testForEachLoopTraversal() {
        fillTableForIteration();
        int count = 0;

        for (Entry<String, Integer> node : table) {
            assertNotNull(node.getKey());
            assertNotNull(node.getValue());
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    void testNoSuchElementException() {
        fillTableForIteration();

        Iterator<Entry<String, Integer>> it = table.iterator();

        it.next();
        it.next();
        it.next();

        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    void testCMEOnExternalAdd() {
        fillTableForIteration();
        Iterator<Entry<String, Integer>> it = table.iterator();

        it.next();

        table.put("date", 40);

        assertThrows(ConcurrentModificationException.class, () -> it.next());
    }

    @Test
    void testCMEOnExternalRemove() {
        fillTableForIteration();
        Iterator<Entry<String, Integer>> it = table.iterator();

        it.next();

        table.remove("apple");

        assertThrows(ConcurrentModificationException.class, () -> it.hasNext());
    }

    @Test
    void testSafeRemoveUsingIterator() {
        fillTableForIteration();
        Iterator<Entry<String, Integer>> it = table.iterator();

        Entry<String, Integer> pair = it.next();

        assertDoesNotThrow(() -> it.remove());

        assertNull(table.get(pair.getKey()));

        assertEquals(2, table.size());

        assertDoesNotThrow(() -> it.next());
    }

    @Test
    void testForEachRemaining() {
        table.put("Banana", 1);
        table.put("Limon", 2);
        table.put("Apple", 3);
        StringBuilder sb = new StringBuilder();
        table.iterator().forEachRemaining(x -> sb.append(x.getValue()));
        String st = sb.toString();

        assert (st.length() == 3 &&
                st.contains("1") &&
                st.contains("2") &&
                st.contains("3"));
    }

    @Test
    void testDoubleRemove() {
        fillTableForIteration();
        Iterator<Entry<String, Integer>> it = table.iterator();
        it.next();
        it.remove();

        assertThrows(IllegalStateException.class, () -> it.remove());
    }
}