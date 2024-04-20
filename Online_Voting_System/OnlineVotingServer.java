import java.io.*;
import java.net.*;
import java.util.*;

public class OnlineVotingServer {
    private static final int PORT = 8080;
    private static final Map<String, Candidate> candidates = new HashMap<>();
    private static final Map<String, User> users = new HashMap<>();
    private static final Map<String, Integer> votes = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            candidates.put("Candidate1", new Candidate("Candidate1", "John Doe"));
            candidates.put("Candidate2", new Candidate("Candidate2", "Jane Smith"));

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

                    case "VOTE":
                        String[] voteDetails = (String[]) ois.readObject();
                        String username = voteDetails[0];
                        String candidateId = voteDetails[1];
                        if (users.containsKey(username) && candidates.containsKey(candidateId)) {
                            votes.put(candidateId, votes.getOrDefault(candidateId, 0) + 1);
                            oos.writeObject("VOTED");
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "GET_RESULTS":
                        oos.writeObject(votes);
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

class Candidate implements Serializable {
    private String id;
    private String name;

    public Candidate(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
