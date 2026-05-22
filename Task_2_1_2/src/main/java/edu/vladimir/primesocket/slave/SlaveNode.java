package edu.vladimir.primesocket.slave;

import edu.vladimir.primesocket.domain.message.Message;
import edu.vladimir.primesocket.domain.task.Task;
import edu.vladimir.primesocket.services.CachingPrimeChecker;
import edu.vladimir.primesocket.services.Logger;

import java.io.*;
import java.net.Socket;

public class SlaveNode {
    private final String masterHost;
    private final int masterPort;
    private final Logger logger;
    private final CachingPrimeChecker cachingChecker;

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
            input.close();
        } catch (IOException e) {
            logger.info("connection error happened");
        } catch (ClassNotFoundException e) {
            logger.info("class exception happened");
        } finally {
            try {
                if (output != null) output.close();
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
                    return;
                case NEWTASK:
                    Task task = inputMessage.task();

                    boolean hasNonPrime = cachingChecker.isArrayHasNonPrime(task.chunk());

                    logger.info("result of the task [" + task.ID() + "]: hasNonPrime=" + hasNonPrime);
                    sendMessage(output, new Message(hasNonPrime));
                    break;
                default:
                    logger.info("unknown message type");
            }
        }
    }

    private void sendMessage(ObjectOutputStream oos, Message msg) throws IOException {
        oos.writeObject(msg);
        oos.flush();
    }
}
