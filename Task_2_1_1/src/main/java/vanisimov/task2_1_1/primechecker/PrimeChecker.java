package vanisimov.task2_1_1.primechecker;

public class PrimeChecker {

    private PrimeChecker() {}

    public static boolean isPrime(int x) {

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