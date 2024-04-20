import java.io.*;
import java.net.*;
import java.util.*;

public class SupplyChainClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nSupply Chain Management System");
                System.out.println("1. Add Product");
                System.out.println("2. Place Order");
                System.out.println("3. Add Supplier");
                System.out.println("4. Add Customer");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject("ADD_PRODUCT");
                        System.out.print("Enter product ID: ");
                        String productId = scanner.nextLine();
                        System.out.print("Enter product name: ");
                        String productName = scanner.nextLine();
                        System.out.print("Enter product price: ");
                        double productPrice = scanner.nextDouble();
                        System.out.print("Enter product quantity: ");
                        int productQuantity = scanner.nextInt();
                        Product newProduct = new Product(productId, productName, productPrice, productQuantity);
                        oos.writeObject(newProduct);
                        String addProductStatus = (String) ois.readObject();
                        System.out.println("Product Addition Status: " + addProductStatus);
                        break;

                    case 2:
                        oos.writeObject("PLACE_ORDER");
                        System.out.print("Enter order ID: ");
                        String orderId = scanner.nextLine();
                        System.out.print("Enter product ID: ");
                        String orderProductId = scanner.nextLine();
                        System.out.print("Enter order quantity: ");
                        int orderQuantity = scanner.nextInt();
                        System.out.print("Enter customer ID: ");
                        String orderCustomerId = scanner.nextLine();
                        Order newOrder = new Order(orderId, orderProductId, orderQuantity, orderCustomerId);
                        oos.writeObject(newOrder);
                        String placeOrderStatus = (String) ois.readObject();
                        System.out.println("Order Placement Status: " + placeOrderStatus);
                        break;

                    case 3:
                        oos.writeObject("ADD_SUPPLIER");
                        System.out.print("Enter supplier ID: ");
                        String supplierId = scanner.nextLine();
                        System.out.print("Enter supplier name: ");
                        String supplierName = scanner.nextLine();
                        System.out.print("Enter supplier address: ");
                        String supplierAddress = scanner.nextLine();
                        System.out.print("Enter supplier contact number: ");
                        String supplierContactNumber = scanner.nextLine();
                        Supplier newSupplier = new Supplier(supplierId, supplierName, supplierAddress, supplierContactNumber);
                        oos.writeObject(newSupplier);
                        String addSupplierStatus = (String) ois.readObject();
                        System.out.println("Supplier Addition Status: " + addSupplierStatus);
                        break;

                    case 4:
                        oos.writeObject("ADD_CUSTOMER");
                        System.out.print("Enter customer ID: ");
                        String customerId = scanner.nextLine();
                        System.out.print("Enter customer name: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Enter customer address: ");
                        String customerAddress = scanner.nextLine();
                        System.out.print("Enter customer contact number: ");
                        String customerContactNumber = scanner.nextLine();
                        Customer newCustomer = new Customer(customerId, customerName, customerAddress, customerContactNumber);
                        oos.writeObject(newCustomer);
                        String addCustomerStatus = (String) ois.readObject();
                        System.out.println("Customer Addition Status: " + addCustomerStatus);
                        break;

                    case 5:
                        oos.writeObject("EXIT");
                        System.out.println("Exiting the program. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
