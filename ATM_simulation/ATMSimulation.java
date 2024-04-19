import java.util.Scanner;

public class ATMSimulation {
    private static double balance = 1000; // Initial balance is set to 1000
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("ATM Simulation System");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void checkBalance() {
        System.out.printf("Your current balance is: $%.2f%n", balance);
    }

    private static void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }

        balance += amount;
        System.out.printf("Deposit of $%.2f successful! Your new balance is: $%.2f%n", amount, balance);
    }

    private static void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0 || amount > balance) {
            System.out.println("Invalid withdrawal amount or insufficient funds!");
            return;
        }

        balance -= amount;
        System.out.printf("Withdrawal of $%.2f successful! Your new balance is: $%.2f%n", amount, balance);
    }
}
