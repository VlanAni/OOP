package edu.vladimir.primesocket.services;

import edu.vladimir.primesocket.exceptions.NotPrimeNumberException;

import java.util.HashMap;

public class CachingPrimeChecker {
    private final HashMap<Integer, Boolean> primeNumbersMap;

    public CachingPrimeChecker() {
        this.primeNumbersMap = new HashMap<>();
    }

    public boolean isArrayHasNonPrime(int[] array) {
        boolean hasNonPrime = false;

        for (int number : array) {
            if (!this.get(number)) {
                try {
                    this.put(number);
                } catch (NotPrimeNumberException e) {
                    hasNonPrime = true;
                }
            }
        }

        return hasNonPrime;
    }

    public boolean get(int number) {
        return primeNumbersMap.get(number) != null;
    }

    public void put(int number) throws NotPrimeNumberException {
        if (!isPrime(number)) {
            throw new NotPrimeNumberException("not prime number");
        }

        this.primeNumbersMap.put(number, true);
    }

    private boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        }

        return switch (x) {
            case 2, 3 -> true;
            default -> {
                for (int i = 2; i <= (int) Math.sqrt(x); ++i) {
                    if (x % i == 0) {
                        yield false;
                    }
                }
                yield true;
            }
        };
    }
}
