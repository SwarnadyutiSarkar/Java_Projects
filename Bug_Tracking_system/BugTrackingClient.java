import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BugTrackingClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nBug Tracking System");
                System.out.println("1. Register");
                System.out.println("2. Report Bug");
                System.out.println("3. Assign Bug");
                System.out.println("4. Update Bug Status");
                System.out.println("5. Search Bugs");
                System.out.println("6. Exit");
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
                        oos.writeObject("REPORT_BUG");
                        Bug newBug = getBugDetails(scanner);
                        oos.writeObject(newBug);
                        String reportStatus = (String) ois.readObject();
                        System.out.println("Report Status: " + reportStatus);
                        break;

                    case 3:
                        oos.writeObject("ASSIGN_BUG");
                        String[] assignDetails = getAssignDetails(scanner);
                        oos.writeObject(assignDetails);
                        String assignStatus = (String) ois.readObject();
                        System.out.println("Assign Status: " + assignStatus);
                        break;

                    case 4:
                        oos.writeObject("UPDATE_BUG_STATUS");
                        String[] updateDetails = getUpdateDetails(scanner);
                        oos.writeObject(updateDetails);
                        String updateStatus = (String) ois.readObject();
                        System.out.println("Update Status: " + updateStatus);
                        break;

                    case 5:
                        oos.writeObject("SEARCH_BUGS");
                        System.out.print("Enter search query: ");
                        String searchQuery = scanner.nextLine();
                        oos.writeObject(searchQuery);
                        Object searchResultsObj = ois.readObject();
                        if (searchResultsObj instanceof List) {
                            List<Bug> searchResults = (List<Bug>) searchResultsObj;
                            System.out.println("Search Results:");
                            for (Bug bug : searchResults) {
                                System.out.println(bug);
                            }
                        } else {
                            System.out.println("No bugs found.");
                        }
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

    private static Bug getBugDetails(Scanner scanner) {
        System.out.print("Enter bug title: ");
        String title = scanner.nextLine();
        System.out.print("Enter bug description: ");
        String description = scanner.nextLine();
        return new Bug(title, description);
    }

    private static String[] getAssignDetails(Scanner scanner) {
        System.out.print("Enter bug ID: ");
        String bugId = scanner.nextLine();
        System.out.print("Enter developer username: ");
        String developerUsername = scanner.nextLine();
        return new String[]{bugId, developerUsername};
    }

    private static String[] getUpdateDetails(Scanner scanner) {
        System.out.print("Enter bug ID: ");
        String bugId = scanner.nextLine();
        System.out.print("Enter new status: ");
        String newStatus = scanner.nextLine();
        return new String[]{bugId, newStatus};
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

class Bug implements Serializable {
    private static int counter = 0;
    private String id;
    private String title;
    private String description;
    private String assignedDeveloper;
    private String status;

    public Bug(String title, String description) {
        this.id = "B" + (++counter);
        this.title = title;
        this.description = description;
        this.status = "NEW";
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public void setAssignedDeveloper(String assignedDeveloper) {
        this.assignedDeveloper = assignedDeveloper;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bug ID: " + id + ", Title: " + title + ", Description: " + description
               + ", Assigned Developer: " + assignedDeveloper + ", Status: " + status;
    }
}
