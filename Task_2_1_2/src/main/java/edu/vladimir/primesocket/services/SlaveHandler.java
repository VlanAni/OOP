package edu.vladimir.primesocket.services;

import edu.vladimir.primesocket.domain.message.Message;
import edu.vladimir.primesocket.domain.message.MessageType;
import edu.vladimir.primesocket.domain.task.Task;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class SlaveHandler implements Runnable {
    private final Socket socket;
    private final Queue<Task> taskQueue;
    private final AtomicBoolean foundNonPrime;
    private final CountDownLatch tasksCompletedLatch;
    private final Logger logger;

    public SlaveHandler(Socket socket,
                        Queue<Task> taskQueue,
                        AtomicBoolean foundNonPrime,
                        CountDownLatch tasksCompletedLatch) {
        this.socket = socket;
        this.taskQueue = taskQueue;
        this.foundNonPrime = foundNonPrime;
        this.tasksCompletedLatch = tasksCompletedLatch;
        this.logger = new Logger("handler [" + socket.getInetAddress().getHostAddress() + "] >>> ");
    }

    @Override
    public void run() {
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            socket.setSoTimeout(200);

            output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.flush();
            input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            while (!foundNonPrime.get()) {
                Task task = taskQueue.poll();

                if (task == null) {
                    break;
                }

                try {
                    sendMessage(output, new Message(task));

                    boolean taskDone = false;
                    while (!taskDone && !foundNonPrime.get()) {
                        try {
                            Message response = (Message) input.readObject();
                            if (response.type() == MessageType.TASKRESULT) {
                                handleResult(response.result());
                                taskDone = true;
                            }
                        } catch (SocketTimeoutException ignored) {
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    if (!foundNonPrime.get()) {
                        logger.info("connection error. Put task[" + task.ID() + "] into the queue");
                        taskQueue.add(task);
                    } else {
                        logger.info("connection error, but non-prime already found. Discarding task[" + task.ID() + "]");
                    }
                    return;
                }
            }

            sendMessage(output, new Message());
        } catch (IOException e) {
            logger.info("io-errors " + e.getMessage());
        } finally {
            try {
                if (output != null) output.close();
                if (input != null) input.close();
            } catch (IOException e) {
                logger.info("error closing streams");
            } finally {
                closeSocket();
            }
        }
    }

    private void handleResult(boolean hasNonPrime) {
        if (hasNonPrime) {
            foundNonPrime.set(true);
            logger.info("non-prime number found");
            while (tasksCompletedLatch.getCount() > 0) {
                tasksCompletedLatch.countDown();
            }
        } else {
            tasksCompletedLatch.countDown();
        }
    }

    private void sendMessage(ObjectOutputStream oos, Message msg) throws IOException {
        oos.writeObject(msg);
        oos.flush();
    }

    private void closeSocket() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ignored) {}
    }
}
