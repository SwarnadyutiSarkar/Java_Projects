import java.util.*;

public class HelpdeskTicketingSystem {
    private Map<String, User> users;
    private Map<String, Category> categories;
    private List<Ticket> tickets;

    public HelpdeskTicketingSystem() {
        this.users = new HashMap<>();
        this.categories = new HashMap<>();
        this.tickets = new ArrayList<>();
    }

    public void addUser(String userId, String userName, String email, String role) {
        if (!users.containsKey(userId)) {
            User user = new User(userId, userName, email, role);
            users.put(userId, user);
            System.out.println("User added successfully.");
        } else {
            System.out.println("User already exists.");
        }
    }

    public void addCategory(String categoryId, String categoryName) {
        if (!categories.containsKey(categoryId)) {
            Category category = new Category(categoryId, categoryName);
            categories.put(categoryId, category);
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Category already exists.");
        }
    }

    public void createTicket(String ticketId, String userId, String categoryId, String description) {
        if (users.containsKey(userId) && categories.containsKey(categoryId)) {
            Ticket ticket = new Ticket(ticketId, userId, categoryId, description, "Open");
            tickets.add(ticket);
            System.out.println("Ticket created successfully.");
        } else {
            System.out.println("User or category not found.");
        }
    }

    public void updateTicketStatus(String ticketId, String status) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            ticket.setStatus(status);
            System.out.println("Ticket status updated successfully.");
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public Ticket findTicketById(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }

    public void displayTickets() {
        System.out.println("Tickets:");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public static void main(String[] args) {
        HelpdeskTicketingSystem system = new HelpdeskTicketingSystem();

        // Add users
        system.addUser("U001", "Alice", "alice@example.com", "User");
        system.addUser("U002", "Bob", "bob@example.com", "Admin");

        // Add categories
        system.addCategory("C001", "Hardware");
        system.addCategory("C002", "Software");

        // Create ticket
        system.createTicket("T001", "U001", "C001", "Computer not starting.");

        // Update ticket status
        system.updateTicketStatus("T001", "Closed");

        // Display tickets
        system.displayTickets();
    }
}

class User {
    private String userId;
    private String userName;
    private String email;
    private String role;

    public User(String userId, String userName, String email, String role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

class Category {
    private String categoryId;
    private String categoryName;

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}

class Ticket {
    private String ticketId;
    private String userId;
    private String categoryId;
    private String description;
    private String status;

    public Ticket(String ticketId, String userId, String categoryId, String description, String status) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.description = description;
        this.status = status;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", userId='" + userId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
