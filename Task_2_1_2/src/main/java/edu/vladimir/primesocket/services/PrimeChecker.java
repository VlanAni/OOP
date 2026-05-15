package edu.vladimir.primesocket.services;

import java.util.Arrays;

public class PrimeChecker {

    private PrimeChecker() {}

    public static boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        }

        switch (x) {
            case 2:
            case 3:
                return true;
            default:
                for (int i = 2; i <= (int) Math.sqrt(x); ++i) {
                    if (x % i == 0) {
                        return false;
                    }
                }
                return true;
        }
    }

    public static boolean checkArray(int[] array) {
        return Arrays.stream(array).parallel().anyMatch(x -> !isPrime(x));
    }
}