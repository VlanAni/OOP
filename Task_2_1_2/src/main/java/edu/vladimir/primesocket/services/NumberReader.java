package edu.vladimir.primesocket.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumberReader {
    public static int[] ReadInputNumbers() {
        List<Integer> numbers = new ArrayList<>();

        try (Scanner ui = new Scanner(System.in)) {
            while (ui.hasNextInt()) {
                numbers.add(ui.nextInt());
            }
        }

        return numbers.stream().mapToInt(Integer::intValue).toArray();
    }
}
