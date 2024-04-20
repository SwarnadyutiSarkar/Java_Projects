import java.util.*;

public class ContentManagementSystem {
    private static Map<String, User> users = new HashMap<>();
    private static List<Content> contents = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nContent Management System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Create Content");
            System.out.println("4. Edit Content");
            System.out.println("5. Delete Content");
            System.out.println("6. Search Content");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    createContent(scanner);
                    break;
                case 4:
                    editContent(scanner);
                    break;
                case 5:
                    deleteContent(scanner);
                    break;
                case 6:
                    searchContent(scanner);
                    break;
                case 7:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = new User(username, password);
        users.put(username, user);
        System.out.println("Registration successful!");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (!users.containsKey(username)) {
            System.out.println("User not found!");
            return;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = users.get(username);
        if (user.authenticate(password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Incorrect password!");
        }
    }

    private static void createContent(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        if (!users.containsKey(username)) {
            System.out.println("User not found!");
            return;
        }
        System.out.print("Enter content title: ");
        String title = scanner.nextLine();
        System.out.print("Enter content body: ");
        String body = scanner.nextLine();
        Content content = new Content(username, title, body);
        contents.add(content);
        System.out.println("Content created successfully!");
    }

    private static void editContent(Scanner scanner) {
        System.out.print("Enter content ID to edit: ");
        int contentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (contentId < 1 || contentId > contents.size()) {
            System.out.println("Invalid content ID!");
            return;
        }
        Content content = contents.get(contentId - 1);
        System.out.print("Enter new content body: ");
        String newBody = scanner.nextLine();
        content.setBody(newBody);
        System.out.println("Content edited successfully!");
    }

    private static void deleteContent(Scanner scanner) {
        System.out.print("Enter content ID to delete: ");
        int contentId = scanner.nextInt();
        if (contentId < 1 || contentId > contents.size()) {
            System.out.println("Invalid content ID!");
            return;
        }
        contents.remove(contentId - 1);
        System.out.println("Content deleted successfully!");
    }

    private static void searchContent(Scanner scanner) {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        List<Content> searchResults = new ArrayList<>();
        for (Content content : contents) {
            if (content.getTitle().contains(keyword) || content.getBody().contains(keyword)) {
                searchResults.add(content);
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No matching content found!");
        } else {
            System.out.println("Search results:");
            for (Content content : searchResults) {
                System.out.println(content);
            }
        }
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

class Content {
    private String username;
    private String title;
    private String body;

    public Content(String username, String title, String body) {
        this.username = username;
        this.title = title;
        this.body = body;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nBody: " + body + "\nPosted by: " + username;
    }
}
