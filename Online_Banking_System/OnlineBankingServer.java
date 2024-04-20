import java.io.*;
import java.net.*;
import java.util.*;

public class OnlineBankingServer {
    private static final int PORT = 8080;
    private static final Map<String, User> users = new HashMap<>();
    private static final Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            while (true) {
                String action = (String) ois.readObject();

                switch (action) {
                    case "REGISTER":
                        User newUser = (User) ois.readObject();
                        users.put(newUser.getUsername(), newUser);
                        accounts.put(newUser.getUsername(), new Account(newUser.getUsername()));
                        oos.writeObject("REGISTERED");
                        break;

                    case "LOGIN":
                        String[] loginDetails = (String[]) ois.readObject();
                        String username = loginDetails[0];
                        String password = loginDetails[1];
                        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                            oos.writeObject("LOGGED_IN");
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "GET_BALANCE":
                        String balanceUsername = (String) ois.readObject();
                        if (accounts.containsKey(balanceUsername)) {
                            oos.writeObject(accounts.get(balanceUsername).getBalance());
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "DEPOSIT":
                        String[] depositDetails = (String[]) ois.readObject();
                        String depositUsername = depositDetails[0];
                        double depositAmount = Double.parseDouble(depositDetails[1]);
                        if (accounts.containsKey(depositUsername)) {
                            accounts.get(depositUsername).deposit(depositAmount);
                            oos.writeObject("DEPOSITED");
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "WITHDRAW":
                        String[] withdrawDetails = (String[]) ois.readObject();
                        String withdrawUsername = withdrawDetails[0];
                        double withdrawAmount = Double.parseDouble(withdrawDetails[1]);
                        if (accounts.containsKey(withdrawUsername) && accounts.get(withdrawUsername).withdraw(withdrawAmount)) {
                            oos.writeObject("WITHDRAWN");
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "EXIT":
                        return;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}

class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Account implements Serializable {
    private String username;
    private double balance;

    public Account(String username) {
        this.username = username;
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
