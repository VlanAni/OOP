package edu.vladimir.primesocket.slave;

import edu.vladimir.primesocket.domain.message.Message;
import edu.vladimir.primesocket.domain.task.Task;
import edu.vladimir.primesocket.services.Logger;
import edu.vladimir.primesocket.services.PrimeChecker;

import java.io.*;
import java.net.Socket;

public class SlaveNode {
    private final String masterHost;
    private final int masterPort;
    private final Logger logger;

    public SlaveNode(String masterHost, int masterPort) {
        if (masterHost == null) {
            throw new IllegalArgumentException("host must be non-null");
        }

        this.masterHost = masterHost;
        this.masterPort = masterPort;
        this.logger = new Logger("slave >>> ");
    }

    public void run() {
        logger.info("start working...");
        logger.info("open connection");

        try (Socket socket = new Socket(this.masterHost, this.masterPort)) {
            ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.flush();
            ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            loop(input, output);
            input.close();
        } catch (IOException e) {
            logger.info("connection error happened");
        } catch (ClassNotFoundException e) {
            logger.info("class exception happened");
        }

        logger.info("finish");
    }

    public void loop(ObjectInputStream input, ObjectOutputStream output) throws IOException, ClassNotFoundException {
        while (true) {
            Message inputMessage = (Message) input.readObject();

            switch (inputMessage.type()) {
                case SHUTDOWN:
                    logger.info("shotdown...");
                    return;
                case NEWTASK:
                    Task task = inputMessage.task();
                    boolean hasNonPrime = PrimeChecker.checkArray(task.chunk());
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
