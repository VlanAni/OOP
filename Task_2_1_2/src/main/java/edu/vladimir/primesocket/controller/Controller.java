package edu.vladimir.primesocket.controller;

import edu.vladimir.primesocket.master.MasterNode;
import edu.vladimir.primesocket.services.NumberReader;
import edu.vladimir.primesocket.slave.SlaveNode;

public class Controller {
    public static void run(String[] args) {
        if (args == null) {
            return;
        }

        if (args.length == 0) {
            System.out.println("incorrect usage");
            return;
        }

        switch (args[0]) {
            case "slave":
                if (args.length != 3) {
                    System.out.println("incorrect usage");
                } else {
                    String ip = args[1];
                    int port;

                    try {
                        port = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("incorrect usage: cannot get port");
                        return;
                    }

                    SlaveNode slaveNode = new SlaveNode(ip, port);
                    slaveNode.run();
                }
                break;
            case "master":
                if (args.length != 3) {
                    System.out.println("incorrect usage");
                } else {
                    int port, chunkSize;

                    try {
                        port = Integer.parseInt(args[1]);
                        chunkSize = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("incorrect usage: cannot get port and chunk size");
                        return;
                    }

                    int[] numbers = NumberReader.ReadInputNumbers();

                    MasterNode masterNode = new MasterNode(port, numbers, chunkSize);
                    masterNode.run();
                }
                break;
            default:
                System.out.println("incorrect role - only slave and master can be");
        }
    }
}
