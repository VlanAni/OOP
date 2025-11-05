import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import vanisimov.hashtable.elements.HashTable;

public class Tests {

    private HashTable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void testAddAndGet() {

        assertTrue(table.add("key1", 100));

        assertEquals(100, table.getValue("key1"));
    }

    @Test
    void testAddDuplicate() {
        table.add("key1", 100);

        assertFalse(table.add("key1", 200));

        assertEquals(100, table.getValue("key1"));
    }

    @Test
    void testUpdateValue() {
        table.add("key1", 100);

        assertTrue(table.updValue("key1", 200));

        assertEquals(200, table.getValue("key1"));
    }

    @Test
    void testUpdateNonExistent() {
        assertFalse(table.updValue("nonexistent", 100));
    }

    @Test
    void testCheckValue() {
        table.add("key1", 100);

        assertTrue(table.checkValue("key1"));

        assertFalse(table.checkValue("nonexistent"));
    }

    @Test
    void testRemove() {
        table.add("key1", 100);

        assertTrue(table.remove("key1"));

        assertFalse(table.checkValue("key1"));

        assertEquals(0, table.getRecordsAmount());
    }

    @Test
    void testRemoveNonExistent() {
        assertFalse(table.remove("nonexistent"));
    }

    @Test
    void testNullHandling() {

        assertFalse(table.add(null, 100));

        assertFalse(table.add("key", null));

        assertNull(table.getValue(null));

        assertFalse(table.updValue(null, 100));

        assertFalse(table.checkValue(null));

        assertFalse(table.remove(null));

    }

    @Test
    void testResize() {

        for (int i = 0; i < 9000; i++) {
            table.add("key" + i, i);
        }

        assertEquals(8999, table.getValue("key8999"));
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.add("key1", 100);
        table1.add("key2", 200);

        table2.add("key1", 100);
        table2.add("key2", 200);

        assertEquals(table1, table2);
    }

    @Test
    void testNotEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<String, Integer> table2 = new HashTable<>();

        table1.add("key1", 100);
        table2.add("key1", 200);

        assertNotEquals(table1, table2);
    }

    @Test
    void testPrintTable() {
        table.add("key1", 100);
        table.add("key2", 200);

        assertDoesNotThrow(() -> table.printTable());
    }
}