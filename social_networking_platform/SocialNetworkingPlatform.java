import java.util.*;

public class SocialNetworkingPlatform {
    private static Map<String, User> users = new HashMap<>();
    private static List<Post> posts = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nSocial Networking Platform");
            System.out.println("1. Register");
            System.out.println("2. Post");
            System.out.println("3. Follow");
            System.out.println("4. View Timeline");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    post(scanner);
                    break;
                case 3:
                    follow(scanner);
                    break;
                case 4:
                    viewTimeline(scanner);
                    break;
                case 5:
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
        User user = new User(username);
        users.put(username, user);
        System.out.println("Registration successful!");
    }

    private static void post(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        if (!users.containsKey(username)) {
            System.out.println("User not found!");
            return;
        }
        System.out.print("Enter your post: ");
        String content = scanner.nextLine();
        Post post = new Post(username, content);
        posts.add(post);
        System.out.println("Post successful!");
    }

    private static void follow(Scanner scanner) {
        System.out.print("Enter your username: ");
        String followerUsername = scanner.nextLine();
        if (!users.containsKey(followerUsername)) {
            System.out.println("User not found!");
            return;
        }
        System.out.print("Enter username to follow: ");
        String followingUsername = scanner.nextLine();
        if (!users.containsKey(followingUsername)) {
            System.out.println("User not found!");
            return;
        }
        User follower = users.get(followerUsername);
        follower.follow(followingUsername);
        System.out.println(followerUsername + " is now following " + followingUsername);
    }

    private static void viewTimeline(Scanner scanner) {
        System.out.print("Enter username to view timeline: ");
        String username = scanner.nextLine();
        if (!users.containsKey(username)) {
            System.out.println("User not found!");
            return;
        }
        User user = users.get(username);
        List<String> timeline = user.getTimeline();
        System.out.println("Timeline for " + username + ":");
        for (String post : timeline) {
            System.out.println(post);
        }
    }
}

class User {
    private String username;
    private Set<String> following = new HashSet<>();
    private List<String> timeline = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public void follow(String username) {
        following.add(username);
    }

    public void addPostToTimeline(String post) {
        timeline.add(post);
    }

    public List<String> getTimeline() {
        List<String> combinedTimeline = new ArrayList<>();
        for (String followedUser : following) {
            User user = SocialNetworkingPlatform.users.get(followedUser);
            if (user != null) {
                combinedTimeline.addAll(user.timeline);
            }
        }
        combinedTimeline.sort(Comparator.comparingInt(String::length));
        return combinedTimeline;
    }
}

class Post {
    private String username;
    private String content;

    public Post(String username, String content) {
        this.username = username;
        this.content = content;
        User user = SocialNetworkingPlatform.users.get(username);
        if (user != null) {
            user.addPostToTimeline(content);
        }
    }

    @Override
    public String toString() {
        return username + ": " + content;
    }
}
