package edu.vladimir.primesocket.slave;

import edu.vladimir.primesocket.domain.message.Message;
import edu.vladimir.primesocket.domain.task.Task;
import edu.vladimir.primesocket.services.CachingPrimeChecker;
import edu.vladimir.primesocket.services.Logger;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SlaveNode {
    private final String masterHost;
    private final int masterPort;
    private final Logger logger;
    private final CachingPrimeChecker cachingChecker;

    private final ExecutorService computer = Executors.newSingleThreadExecutor();
    private Future<?> localComputation = null;

    public SlaveNode(String masterHost, int masterPort) {
        if (masterHost == null) {
            throw new IllegalArgumentException("host must be non-null");
        }

        this.masterHost = masterHost;
        this.masterPort = masterPort;
        this.cachingChecker = new CachingPrimeChecker();
        this.logger = new Logger("slave >>> ");
    }

    public void run() {
        logger.info("start working...");
        logger.info("open connection");

        Socket socket = null;
        ObjectOutputStream output = null;
        ObjectInputStream input = null;

        try {
            socket = new Socket(this.masterHost, this.masterPort);
            output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.flush();
            input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            loop(input, output);
        } catch (IOException e) {
            logger.info("connection error happened");
        } catch (ClassNotFoundException e) {
            logger.info("class exception happened");
        } finally {
            try {
                if (output != null) output.close();
                if (input != null) input.close();
            } catch (IOException e) {
                logger.info("error closing streams");
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) socket.close();
                } catch (IOException ignored) {}
            }
        }

        logger.info("finish");
    }

    public void loop(ObjectInputStream input, ObjectOutputStream output) throws IOException, ClassNotFoundException {
        while (true) {
            Message inputMessage = (Message) input.readObject();

            switch (inputMessage.type()) {
                case SHUTDOWN:
                    logger.info("shutdown...");

                    if (localComputation != null) {
                        localComputation.cancel(true);
                    }

                    computer.shutdownNow();

                    return;
                case NEWTASK:
                    Task task = inputMessage.task();

                    localComputation = computer.submit(() -> {
                        boolean hasNonPrime = cachingChecker.isArrayHasNonPrime(task.chunk());

                        if (!Thread.currentThread().isInterrupted()) {
                            logger.info("result of the task [" + task.ID() + "]: hasNonPrime=" + hasNonPrime);
                            try {
                                sendMessage(output, new Message(hasNonPrime));
                            } catch (IOException e) {
                                logger.info("failed to send result");
                            }
                        }
                    });
                    break;
                default:
                    logger.info("unknown message type");
            }
        }
    }

    private synchronized void sendMessage(ObjectOutputStream oos, Message msg) throws IOException {
        oos.writeObject(msg);
        oos.flush();
    }
}
