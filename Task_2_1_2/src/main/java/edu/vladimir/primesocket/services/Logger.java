package edu.vladimir.primesocket.services;

public class Logger {
    private final String prefix;

    public Logger (String prefix) {
        this.prefix = prefix;
    }

    public void info(String info) {
        System.out.println(prefix + info);
    }
}
