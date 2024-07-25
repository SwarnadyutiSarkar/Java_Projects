import java.util.Scanner;

public class Main {
    static final int MOD = (int) (1e9 + 7);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        System.out.println(countValidPins(n));
    }

    public static long countValidPins(long n) {
        long evenDigits = 5; // Number of even digits (0, 2, 4, 6, 8)
        long primeDigits = 4; // Number of prime digits (2, 3, 5, 7)

        if (n == 1) {
            // Base case: if n is 1, there are 5 valid PINs (0, 2, 4, 6, 8)
            return evenDigits;
        }

        long result = evenDigits * primeDigits; // Initialize the result for n = 2
        n -= 2; // Subtract 2 from n

        while (n > 0) {
            // For each remaining digit, multiply the result by the number of options
            result = (result * evenDigits) % MOD; // Even digit
            n -= 1;
            if (n > 0) {
                result = (result * primeDigits) % MOD; // Prime digit
                n -= 1;
            }
        }

        return result;
    }
}