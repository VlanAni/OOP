package edu.vladimir.primesocket.master;

import edu.vladimir.primesocket.domain.task.Task;
import edu.vladimir.primesocket.domain.task.TaskID;
import edu.vladimir.primesocket.services.Logger;
import edu.vladimir.primesocket.services.SlaveHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MasterNode {
    private final int port;
    private final Logger logger = new Logger("master >>> ");

    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean foundNonPrime = new AtomicBoolean(false);
    private final CountDownLatch tasksCompletedLatch;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public MasterNode(int port, int[] numbers, int chunkSize) {
        this.port = port;

        int taskId = 0;
        for (int i = 0; i < numbers.length; i += chunkSize) {
            int[] chunk = Arrays.copyOfRange(numbers, i, Math.min(numbers.length, i + chunkSize));
            taskQueue.add(new Task(chunk, new TaskID(taskId++)));
        }

        this.tasksCompletedLatch = new CountDownLatch(taskId);
    }

    public boolean run() {
        logger.info("server is ready: " + tasksCompletedLatch.getCount());

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            startCompletionMonitor(serverSocket);

            while (!serverSocket.isClosed() && !foundNonPrime.get()) {
                try {
                    Socket slaveSocket = serverSocket.accept();
                    executorService.submit(new SlaveHandler(
                            slaveSocket, taskQueue, foundNonPrime, tasksCompletedLatch
                    ));
                } catch (IOException e) {
                    if (!serverSocket.isClosed()) logger.info("accept error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.info("server error: " + e.getMessage());
        } finally {
            shutdown();
        }

        return foundNonPrime.get();
    }

    private void startCompletionMonitor(ServerSocket serverSocket) {
        new Thread(() -> {
            try {
                tasksCompletedLatch.await();
                logger.info("all tasks done or non-prime found. closing server...");
                serverSocket.close();
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void shutdown() {
        executorService.shutdown();
        logger.info("waiting for slaves to finish their current chunks gracefully...");
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                logger.info("timeout reached. Forcing shutdown...");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("total result: " + foundNonPrime.get());
    }
}