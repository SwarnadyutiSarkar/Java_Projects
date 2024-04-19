import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelpdeskManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Ticket> tickets = new ArrayList<>();
    private static List<Agent> agents = new ArrayList<>();

    public static void main(String[] args) {
        initializeAgents();

        while (true) {
            System.out.println("\nHelpdesk Management System");
            System.out.println("1. Create Ticket");
            System.out.println("2. Assign Ticket to Agent");
            System.out.println("3. Mark Ticket as Resolved");
            System.out.println("4. View All Tickets");
            System.out.println("5. View All Agents");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createTicket();
                    break;
                case 2:
                    assignTicket();
                    break;
                case 3:
                    markTicketAsResolved();
                    break;
                case 4:
                    viewAllTickets();
                    break;
                case 5:
                    viewAllAgents();
                    break;
                case 6:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeAgents() {
        agents.add(new Agent(1, "John Doe"));
        agents.add(new Agent(2, "Jane Smith"));
        // Add more agents here
    }

    private static void createTicket() {
        System.out.print("Enter ticket ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter issue description: ");
        String issue = scanner.nextLine();

        Ticket ticket = new Ticket(id, issue);
        tickets.add(ticket);
        System.out.println("Ticket created successfully!");
    }

    private static void assignTicket() {
        System.out.print("Enter ticket ID to assign: ");
        int ticketId = scanner.nextInt();

        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            System.out.println("Available Agents:");
            for (Agent agent : agents) {
                System.out.println(agent);
            }
            System.out.print("Enter agent ID to assign: ");
            int agentId = scanner.nextInt();
            Agent agent = findAgentById(agentId);
            if (agent != null) {
                ticket.setAssignedAgent(agent);
                System.out.println("Ticket assigned to " + agent.getName());
            } else {
                System.out.println("Agent not found!");
            }
        } else {
            System.out.println("Ticket not found!");
        }
    }

    private static void markTicketAsResolved() {
        System.out.print("Enter ticket ID to mark as resolved: ");
        int ticketId = scanner.nextInt();

        Ticket ticket = findTicketById(ticketId);
        if (ticket != null) {
            ticket.setResolved(true);
            System.out.println("Ticket marked as resolved!");
        } else {
            System.out.println("Ticket not found!");
        }
    }

    private static void viewAllTickets() {
        System.out.println("All Tickets:");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void viewAllAgents() {
        System.out.println("All Agents:");
        for (Agent agent : agents) {
            System.out.println(agent);
        }
    }

    private static Ticket findTicketById(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    private static Agent findAgentById(int id) {
        for (Agent agent : agents) {
            if (agent.getId() == id) {
                return agent;
            }
        }
        return null;
    }
}

class Ticket {
    private int id;
    private String issue;
    private Agent assignedAgent;
    private boolean resolved;

    public Ticket(int id, String issue) {
        this.id = id;
        this.issue = issue;
        this.resolved = false;
    }

    public int getId() {
        return id;
    }

    public String getIssue() {
        return issue;
    }

    public Agent getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(Agent assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + id + ", Issue: " + issue + ", Assigned Agent: " + (assignedAgent != null ? assignedAgent.getName() : "Unassigned") + ", Resolved: " + resolved;
    }
}

class Agent {
    private int id;
    private String name;

    public Agent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Agent ID: " + id + ", Name: " + name;
    }
}
