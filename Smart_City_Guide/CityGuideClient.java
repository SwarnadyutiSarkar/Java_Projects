import java.io.*;
import java.net.*;
import java.util.*;

public class CityGuideClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nCity Guide Project");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. View Locations");
                System.out.println("4. Exit");
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
                        oos.writeObject("GET_LOCATIONS");
                        Object locationsObj = ois.readObject();
                        if (locationsObj instanceof List) {
                            List<Location> locations = (List<Location>) locationsObj;
                            System.out.println("Locations:");
                            for (Location location : locations) {
                                System.out.println("ID: " + location.getId());
                                System.out.println("Name: " + location.getName());
                                System.out.println("Category: " + location.getCategory());
                                System.out.println("Description: " + location.getDescription());
                                System.out.println("-------------------------");
                            }
                        } else {
                            System.out.println("No locations available.");
                        }
                        break;

                    case 4:
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
