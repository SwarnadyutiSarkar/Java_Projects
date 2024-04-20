import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RideSharingClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nRide Sharing App");
                System.out.println("1. Register");
                System.out.println("2. Book Ride");
                System.out.println("3. Update Ride");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject("REGISTER");
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        User newUser = new User(username);
                        oos.writeObject(newUser);
                        String registrationStatus = (String) ois.readObject();
                        System.out.println("Registration Status: " + registrationStatus);
                        break;

                    case 2:
                        oos.writeObject("BOOK_RIDE");
                        Ride rideRequest = getRideDetails(scanner);
                        oos.writeObject(rideRequest);
                        Object response = ois.readObject();
                        if (response instanceof Ride) {
                            Ride matchedRide = (Ride) response;
                            System.out.println("Matched Ride: " + matchedRide);
                        } else {
                            System.out.println("No rides available.");
                        }
                        break;

                    case 3:
                        oos.writeObject("UPDATE_RIDE");
                        Ride updatedRide = getRideDetails(scanner);
                        oos.writeObject(updatedRide);
                        String updateStatus = (String) ois.readObject();
                        System.out.println("Update Status: " + updateStatus);
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

    private static Ride getRideDetails(Scanner scanner) {
        System.out.print("Enter start location: ");
        String startLocation = scanner.nextLine();
        System.out.print("Enter end location: ");
        String endLocation = scanner.nextLine();
        System.out.print("Enter fare: ");
        double fare = scanner.nextDouble();
        return new Ride(startLocation, endLocation, fare);
    }
}

class User implements Serializable {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

class Ride implements Serializable {
    private String startLocation;
    private String endLocation;
    private double fare;

    public Ride(String startLocation, String endLocation, double fare) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fare = fare;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public double getFare() {
        return fare;
    }

    @Override
    public String toString() {
        return "Start Location: " + startLocation + ", End Location: " + endLocation + ", Fare: $" + fare;
    }
}
