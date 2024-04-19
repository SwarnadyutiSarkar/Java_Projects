import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    searchStudentByName();
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

    private static void addStudent() {
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student email: ");
        String email = scanner.nextLine();

        Student student = new Student(id, name, email);
        students.add(student);
        System.out.println("Student added successfully!");
    }

    private static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display!");
            return;
        }

        System.out.println("Student List:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudentById() {
        System.out.print("Enter student ID to search: ");
        int id = scanner.nextInt();

        boolean found = false;
        for (Student student : students) {
            if (student.getId() == id) {
                System.out.println("Student found:");
                System.out.println(student);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found!");
        }
    }

    private static void searchStudentByName() {
        System.out.print("Enter student name to search: ");
        String name = scanner.nextLine();

        boolean found = false;
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println("Student found:");
                System.out.println(student);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found!");
        }
    }
}

class Student {
    private int id;
    private String name;
    private String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}
