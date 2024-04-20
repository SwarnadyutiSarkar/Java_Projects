import java.io.*;
import java.net.*;
import java.util.Scanner;

public class OnlineVotingClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nOnline Voting System");
                System.out.println("1. Register");
                System.out.println("2. Vote");
                System.out.println("3. Get Results");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject("REGISTER");
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        User newUser = new User(username, password);
                        oos.writeObject(newUser);
                        String registrationStatus = (String) ois.readObject();
                        System.out.println("Registration Status: " + registrationStatus);
                        break;

                    case 2:
                        oos.writeObject("VOTE");
                        System.out.print("Enter username: ");
                        String voteUsername = scanner.nextLine();
                        System.out.println("Candidates:");
                        System.out.println("Candidate1 - John Doe");
                        System.out.println("Candidate2 - Jane Smith");
                        System.out.print("Enter candidate ID to vote: ");
                        String candidateId = scanner.nextLine();
                        String[] voteDetails = {voteUsername, candidateId};
                        oos.writeObject(voteDetails);
                        String voteStatus = (String) ois.readObject();
                        System.out.println("Vote Status: " + voteStatus);
                        break;

                    case 3:
                        oos.writeObject("GET_RESULTS");
                        Object resultsObj = ois.readObject();
                        if (resultsObj instanceof Map) {
                            Map<String, Integer> results = (Map<String, Integer>) resultsObj;
                            System.out.println("Voting Results:");
                            for (Map.Entry<String, Integer> entry : results.entrySet()) {
                                System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
                            }
                        } else {
                            System.out.println("No results available.");
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
