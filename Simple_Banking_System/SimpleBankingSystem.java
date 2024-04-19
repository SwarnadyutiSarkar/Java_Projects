import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimpleBankingSystem {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to Simple Banking System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        if (initialBalance < 0) {
            System.out.println("Invalid initial balance!");
            return;
        }

        Account account = new Account(accountNumber, initialBalance);
        accounts.put(accountNumber, account);
        System.out.println("Account created successfully!");
    }

    private static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (!accounts.containsKey(accountNumber)) {
            System.out.println("Account does not exist!");
            return;
        }

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }

        accounts.get(accountNumber).deposit(amount);
        System.out.println("Deposit successful!");
    }

    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (!accounts.containsKey(accountNumber)) {
            System.out.println("Account does not exist!");
            return;
        }

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0 || amount > accounts.get(accountNumber).getBalance()) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }

        accounts.get(accountNumber).withdraw(amount);
        System.out.println("Withdrawal successful!");
    }

    private static void checkBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (!accounts.containsKey(accountNumber)) {
            System.out.println("Account does not exist!");
            return;
        }

        System.out.printf("Balance for account %s: $%.2f%n", accountNumber, accounts.get(accountNumber).getBalance());
    }
}

class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}
