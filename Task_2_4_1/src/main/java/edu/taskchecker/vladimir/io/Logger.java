package edu.taskchecker.vladimir.io;

public class Logger {
    private final String prefix;

    public Logger(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("msut be non null");
        }

        this.prefix = prefix;
    }

    public synchronized void printInfo(String message) {
        if (message == null) {
            return;
        }

        System.out.println(createOut(message));
    }

    public synchronized void printError(String message) {
        if (message == null) {
            return;
        }

        System.err.println(createOut(message));
    }

    private synchronized String createOut(String message) {
        return new StringBuilder().append(prefix).append(message).toString();
    }
}
