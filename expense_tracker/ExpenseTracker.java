import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseTracker {
    private static List<Double> expenses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addExpense() {
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid expense amount!");
            return;
        }

        expenses.add(amount);
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        System.out.println("Expenses:");

        for (int i = 0; i < expenses.size(); i++) {
            System.out.printf("%d. $%.2f%n", i + 1, expenses.get(i));
        }

        double total = expenses.stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("Total: $%.2f%n", total);
    }
}
