import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static Map<String, Book> books = new HashMap<>();
    private static List<Issue> issuedBooks = new ArrayList<>();
    private static int issueCounter = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Display Available Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    issueBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    displayAvailableBooks();
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

    private static void addBook() {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        if (books.containsKey(isbn)) {
            System.out.println("Book already exists!");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        Book book = new Book(isbn, title, author);
        books.put(isbn, book);
        System.out.println("Book added successfully!");
    }

    private static void issueBook() {
        System.out.print("Enter ISBN of the book to be issued: ");
        String isbn = scanner.nextLine();

        if (!books.containsKey(isbn)) {
            System.out.println("Book not found!");
            return;
        }

        if (books.get(isbn).isIssued()) {
            System.out.println("Book already issued!");
            return;
        }

        System.out.print("Enter borrower's name: ");
        String borrowerName = scanner.nextLine();

        Issue issue = new Issue(issueCounter++, isbn, borrowerName);
        issuedBooks.add(issue);
        books.get(isbn).setIssued(true);

        System.out.println("Book issued successfully!");
    }

    private static void returnBook() {
        System.out.print("Enter ISBN of the book to be returned: ");
        String isbn = scanner.nextLine();

        if (!books.containsKey(isbn)) {
            System.out.println("Book not found!");
            return;
        }

        if (!books.get(isbn).isIssued()) {
            System.out.println("Book is not issued!");
            return;
        }

        Issue issueToRemove = null;
        for (Issue issue : issuedBooks) {
            if (issue.getIsbn().equals(isbn)) {
                issueToRemove = issue;
                break;
            }
        }

        issuedBooks.remove(issueToRemove);
        books.get(isbn).setIssued(false);

        System.out.println("Book returned successfully!");
    }

    private static void displayAvailableBooks() {
        System.out.println("Available Books:");
        for (Map.Entry<String, Book> entry : books.entrySet()) {
            if (!entry.getValue().isIssued()) {
                System.out.println(entry.getValue());
            }
        }
    }
}

class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean issued;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + ", Title: " + title + ", Author: " + author;
    }
}

class Issue {
    private int issueId;
    private String isbn;
    private String borrowerName;

    public Issue(int issueId, String isbn, String borrowerName) {
        this.issueId = issueId;
        this.isbn = isbn;
        this.borrowerName = borrowerName;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Issue ID: " + issueId + ", ISBN: " + isbn + ", Borrower: " + borrowerName;
    }
}
