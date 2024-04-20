import java.io.*;
import java.net.*;
import java.util.*;

public class CityGuideServer {
    private static final int PORT = 8080;
    private static final Map<String, User> users = new HashMap<>();
    private static final Map<String, Location> locations = new HashMap<>();

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

                    case "GET_LOCATIONS":
                        oos.writeObject(new ArrayList<>(locations.values()));
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

class Location implements Serializable {
    private String id;
    private String name;
    private String category;
    private String description;

    public Location(String id, String name, String category, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
