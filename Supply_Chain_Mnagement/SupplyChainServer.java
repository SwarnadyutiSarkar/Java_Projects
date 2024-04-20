import java.io.*;
import java.net.*;
import java.util.*;

public class SupplyChainServer {
    private static final int PORT = 8080;
    private static final Map<String, Product> products = new HashMap<>();
    private static final Map<String, Order> orders = new HashMap<>();
    private static final Map<String, Supplier> suppliers = new HashMap<>();
    private static final Map<String, Customer> customers = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            while (true) {
                String action = (String) ois.readObject();

                switch (action) {
                    case "ADD_PRODUCT":
                        Product newProduct = (Product) ois.readObject();
                        products.put(newProduct.getId(), newProduct);
                        oos.writeObject("PRODUCT_ADDED");
                        break;

                    case "PLACE_ORDER":
                        Order newOrder = (Order) ois.readObject();
                        orders.put(newOrder.getId(), newOrder);
                        oos.writeObject("ORDER_PLACED");
                        break;

                    case "ADD_SUPPLIER":
                        Supplier newSupplier = (Supplier) ois.readObject();
                        suppliers.put(newSupplier.getId(), newSupplier);
                        oos.writeObject("SUPPLIER_ADDED");
                        break;

                    case "ADD_CUSTOMER":
                        Customer newCustomer = (Customer) ois.readObject();
                        customers.put(newCustomer.getId(), newCustomer);
                        oos.writeObject("CUSTOMER_ADDED");
                        break;

                    case "EXIT":
                        return;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}

class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

class Order implements Serializable {
    private String id;
    private String productId;
    private int quantity;
    private String customerId;

    public Order(String id, String productId, int quantity, String customerId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomerId() {
        return customerId;
    }
}

class Supplier implements Serializable {
    private String id;
    private String name;
    private String address;
    private String contactNumber;

    public Supplier(String id, String name, String address, String contactNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}

class Customer implements Serializable {
    private String id;
    private String name;
    private String address;
    private String contactNumber;

    public Customer(String id, String name, String address, String contactNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
