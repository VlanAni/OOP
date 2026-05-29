import edu.vladimir.primesocket.master.MasterNode;
import edu.vladimir.primesocket.slave.SlaveNode;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IntegrationTests {
    @Test
    void testPrime() throws InterruptedException{
        int[] primeArray = {
                20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053
        };

        integrationTestWithTwoSlave(9998, 3, primeArray, false);
    }

    @Test
    void testNonPrime() throws InterruptedException{
        int[] nonPrimeArray = {6, 8, 7, 13, 5, 9, 4};

        integrationTestWithTwoSlave(9999, 2, nonPrimeArray, true);
    }


    private void integrationTestWithTwoSlave(
            int port,
            int chunkSize,
            int[] array,
            boolean expected
    ) throws InterruptedException {
        AtomicBoolean result = new AtomicBoolean();

        Thread masterThread = new Thread(() -> {
            MasterNode masterNode = new MasterNode(port, array, chunkSize);
            result.set(masterNode.run());
        });
        masterThread.setDaemon(true);
        masterThread.start();

        sleep(500);

        Thread slave1Thread = new Thread(() -> {
            SlaveNode slave1 = new SlaveNode("localhost", port);
            slave1.run();
        });
        slave1Thread.setDaemon(true);
        slave1Thread.start();

        Thread slave2Thread = new Thread(() -> {
            SlaveNode slave2 = new SlaveNode("localhost", port);
            slave2.run();
        });
        slave2Thread.setDaemon(true);
        slave2Thread.start();

        masterThread.join(10000);

        if (masterThread.isAlive()) {
            fail("too long...");
        }

        assertEquals(expected, result.get());
    }
}
