import java.util.Scanner;

public class Currency_Converter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Currency Converter");
        System.out.println("1. USD to EUR");
        System.out.println("2. EUR to USD");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter amount in USD: ");
                double usdAmount = scanner.nextDouble();
                double eurAmount = usdToEur(usdAmount);
                System.out.printf("%.2f USD = %.2f EUR\n", usdAmount, eurAmount);
                break;
            case 2:
                System.out.print("Enter amount in EUR: ");
                double eurAmount2 = scanner.nextDouble();
                double usdAmount2 = eurToUsd(eurAmount2);
                System.out.printf("%.2f EUR = %.2f USD\n", eurAmount2, usdAmount2);
                break;
            default:
                System.out.println("Invalid choice!");
        }

        scanner.close();
    }

    public static double usdToEur(double usdAmount) {
        // Exchange rate as of the last update
        double exchangeRate = 0.90;
        return usdAmount * exchangeRate;
    }

    public static double eurToUsd(double eurAmount) {
        // Exchange rate as of the last update
        double exchangeRate = 1.11;
        return eurAmount * exchangeRate;
    }
}
