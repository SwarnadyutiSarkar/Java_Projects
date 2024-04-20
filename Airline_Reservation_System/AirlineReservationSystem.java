import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AirlineReservationSystem {
    private static Map<Integer, Flight> flights = new HashMap<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static int reservationIdCounter = 1;

    public static void main(String[] args) {
        initializeFlights();

        while (true) {
            System.out.println("\nAirline Reservation System");
            System.out.println("1. Book Flight");
            System.out.println("2. View Available Flights");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    bookFlight(scanner);
                    break;
                case 2:
                    viewAvailableFlights();
                    break;
                case 3:
                    cancelReservation(scanner);
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

    private static void initializeFlights() {
        flights.put(1, new Flight(1, "AA101", "New York", "Los Angeles", 200, 250.0));
        flights.put(2, new Flight(2, "BB202", "Chicago", "Houston", 150, 200.0));
        flights.put(3, new Flight(3, "CC303", "Miami", "Denver", 180, 220.0));
    }

    private static void bookFlight(Scanner scanner) {
        System.out.print("Enter flight ID to book: ");
        int flightId = scanner.nextInt();
        if (flights.containsKey(flightId)) {
            Flight flight = flights.get(flightId);
            System.out.print("Enter passenger name: ");
            String passengerName = scanner.next();
            Reservation reservation = new Reservation(reservationIdCounter++, flight, passengerName);
            reservations.add(reservation);
            System.out.println("Reservation successful! Reservation ID: " + reservation.getReservationId());
        } else {
            System.out.println("Flight not found!");
        }
    }

    private static void viewAvailableFlights() {
        System.out.println("Available Flights:");
        for (Flight flight : flights.values()) {
            System.out.println(flight);
        }
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();
        boolean removed = reservations.removeIf(reservation -> reservation.getReservationId() == reservationId);
        if (removed) {
            System.out.println("Reservation cancelled successfully!");
        } else {
            System.out.println("Reservation not found!");
        }
    }
}

class Flight {
    private int id;
    private String flightNumber;
    private String source;
    private String destination;
    private int capacity;
    private double price;

    public Flight(int id, String flightNumber, String source, String destination, int capacity, double price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Flight " + flightNumber + " from " + source + " to " + destination + " - Price: $" + price;
    }
}

class Reservation {
    private int reservationId;
    private Flight flight;
    private String passengerName;

    public Reservation(int reservationId, Flight flight, String passengerName) {
        this.reservationId = reservationId;
        this.flight = flight;
        this.passengerName = passengerName;
    }

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Passenger: " + passengerName + ", Flight: " + flight.getFlightNumber();
    }
}
