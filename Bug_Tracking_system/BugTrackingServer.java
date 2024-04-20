import java.io.*;
import java.net.*;
import java.util.*;

public class BugTrackingServer {
    private static final int PORT = 8080;
    private static final Map<String, User> users = new HashMap<>();
    private static final List<Bug> bugs = new ArrayList<>();

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

                    case "REPORT_BUG":
                        Bug newBug = (Bug) ois.readObject();
                        bugs.add(newBug);
                        oos.writeObject("BUG_REPORTED");
                        break;

                    case "ASSIGN_BUG":
                        String[] assignDetails = (String[]) ois.readObject();
                        String bugId = assignDetails[0];
                        String developerUsername = assignDetails[1];
                        assignBugToDeveloper(bugId, developerUsername);
                        oos.writeObject("BUG_ASSIGNED");
                        break;

                    case "UPDATE_BUG_STATUS":
                        String[] updateDetails = (String[]) ois.readObject();
                        String updatedBugId = updateDetails[0];
                        String newStatus = updateDetails[1];
                        updateBugStatus(updatedBugId, newStatus);
                        oos.writeObject("STATUS_UPDATED");
                        break;

                    case "SEARCH_BUGS":
                        String searchQuery = (String) ois.readObject();
                        List<Bug> searchResults = searchBugs(searchQuery);
                        oos.writeObject(searchResults);
                        break;

                    case "EXIT":
                        return;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    private static void assignBugToDeveloper(String bugId, String developerUsername) {
        for (Bug bug : bugs) {
            if (bug.getId().equals(bugId)) {
                bug.setAssignedDeveloper(developerUsername);
                break;
            }
        }
    }

    private static void updateBugStatus(String bugId, String newStatus) {
        for (Bug bug : bugs) {
            if (bug.getId().equals(bugId)) {
                bug.setStatus(newStatus);
                break;
            }
        }
    }

    private static List<Bug> searchBugs(String searchQuery) {
        List<Bug> searchResults = new ArrayList<>();
        for (Bug bug : bugs) {
            if (bug.getTitle().contains(searchQuery) || bug.getDescription().contains(searchQuery)) {
                searchResults.add(bug);
            }
        }
        return searchResults;
    }
}
