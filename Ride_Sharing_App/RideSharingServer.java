import java.io.*;
import java.net.*;
import java.util.*;

public class RideSharingServer {
    private static final int PORT = 8080;
    private static final Map<String, User> users = new HashMap<>();
    private static final List<Ride> rides = new ArrayList<>();

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
                        oos.writeObject("REGISTERED");
                        break;

                    case "BOOK_RIDE":
                        Ride rideRequest = (Ride) ois.readObject();
                        Ride matchedRide = findMatchedRide(rideRequest);
                        if (matchedRide != null) {
                            rides.remove(matchedRide);
                            oos.writeObject(matchedRide);
                        } else {
                            oos.writeObject("NO_RIDE_AVAILABLE");
                        }
                        break;

                    case "UPDATE_RIDE":
                        Ride updatedRide = (Ride) ois.readObject();
                        rides.add(updatedRide);
                        oos.writeObject("RIDE_UPDATED");
                        break;

                    case "EXIT":
                        return;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    private static Ride findMatchedRide(Ride rideRequest) {
        for (Ride ride : rides) {
            if (ride.getStartLocation().equals(rideRequest.getStartLocation())
                && ride.getEndLocation().equals(rideRequest.getEndLocation())) {
                return ride;
            }
        }
        return null;
    }
}
