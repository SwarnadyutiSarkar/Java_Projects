import java.io.*;
import java.net.*;
import java.util.Scanner;

public class OnlineBankingClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nOnline Banking System");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Check Balance");
                System.out.println("4. Deposit");
                System.out.println("5. Withdraw");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject("REGISTER");
                        System.out.print("Enter username: ");
                        String regUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String regPassword = scanner.nextLine();
                        User newUser = new User(regUsername, regPassword);
                        oos.writeObject(newUser);
                        String regStatus = (String) ois.readObject();
                        System.out.println("Registration Status: " + regStatus);
                        break;

                    case 2:
                        oos.writeObject("LOGIN");
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        String[] loginDetails = {loginUsername, loginPassword};
                        oos.writeObject(loginDetails);
                        String loginStatus = (String) ois.readObject();
                        System.out.println("Login Status: " + loginStatus);
                        break;

                    case 3:
                        oos.writeObject("GET_BALANCE");
                        System.out.print("Enter username: ");
                        String balanceUsername = scanner.nextLine();
                        oos.writeObject(balanceUsername);
                        Object balanceObj = ois.readObject();
                        if (balanceObj instanceof Double) {
                            System.out.println("Balance: $" + balanceObj);
                        } else {
                            System.out.println("Error fetching balance.");
                        }
                        break;

                    case 4:
                        oos.writeObject("DEPOSIT");
                        System.out.print("Enter username: ");
                        String depositUsername = scanner.nextLine();
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        String[] depositDetails = {depositUsername, String.valueOf(depositAmount)};
                        oos.writeObject(depositDetails);
                        String depositStatus = (String) ois.readObject();
                        System.out.println("Deposit Status: " + depositStatus);
                        break;

                    case 5:
                        oos.writeObject("WITHDRAW");
                        System.out.print("Enter username: ");
                        String withdrawUsername = scanner.nextLine();
                        System.out.print("Enter withdraw amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        String[] withdrawDetails = {withdrawUsername, String.valueOf(withdrawAmount)};
                        oos.writeObject(withdrawDetails);
                        String withdrawStatus = (String) ois.readObject();
                        System.out.println("Withdraw Status: " + withdrawStatus);
                        break;

                    case 6:
                        oos.writeObject("EXIT");
                        System.out.println("Exiting the program. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
