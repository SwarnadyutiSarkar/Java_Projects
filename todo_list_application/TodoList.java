import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoList {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nTodo List");
            System.out.println("1. Add Task");
            System.out.println("2. Mark Task as Completed");
            System.out.println("3. View All Tasks");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    markTaskAsCompleted();
                    break;
                case 3:
                    viewAllTasks();
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        Task task = new Task(description);
        tasks.add(task);
        System.out.println("Task added successfully!");
    }

    private static void markTaskAsCompleted() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to mark as completed!");
            return;
        }

        viewAllTasks();
        System.out.print("Enter task number to mark as completed: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setCompleted(true);
            System.out.println("Task marked as completed!");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    private static void viewAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display!");
            return;
        }

        System.out.println("Your Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}

class Task {
    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Description: " + description + ", Completed: " + completed;
    }
}
