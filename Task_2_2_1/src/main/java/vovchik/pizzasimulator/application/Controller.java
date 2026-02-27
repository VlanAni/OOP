package vovchik.pizzasimulator.application;

import vovchik.pizzasimulator.formatter.Formatter;
import vovchik.pizzasimulator.pizzeria.Pizzeria;

import java.util.Scanner;

public class Controller {

    static void run() {
        getUserInput();

        Pizzeria pizzeria = new Pizzeria("settings.json",
                UserInput.ordersPath);

        pizzeria.start();

        try {
            Thread.sleep(UserInput.workingTime * 1000L);
        } catch (InterruptedException e) {
            Formatter.logSystem("Внезапное прекращение работы");
            throw new RuntimeException(e);
        }

        pizzeria.shutdown();
    }

    private static void getUserInput() {
        try (Scanner scn = new Scanner(System.in)) {
            Formatter.logSystem("Введите время работы пиццерии в секундах");
            UserInput.workingTime = scn.nextInt();
            scn.nextLine();
            Formatter.logSystem("Введите адрес файла, в котором содержутся заказы");
            UserInput.ordersPath = scn.nextLine();
        }
    }

    private static class UserInput {
        static int workingTime;
        static String ordersPath;
    }
}
