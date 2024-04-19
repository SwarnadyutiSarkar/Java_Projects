import java.util.Scanner;

public class AdventureGame {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Text-Based Adventure Game");
        System.out.println("You wake up in a mysterious room...");

        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Look around");
            System.out.println("2. Open the door");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("You see a table and a chair.");
                    break;
                case 2:
                    System.out.println("The door is locked.");
                    break;
                case 3:
                    System.out.println("Exiting the game. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
