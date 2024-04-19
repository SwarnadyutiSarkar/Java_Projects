import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SupermarketBillingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        initializeProducts();

        List<Product> cart = new ArrayList<>();

        while (true) {
            System.out.println("\nSupermarket Billing System");
            System.out.println("1. Add Product to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Generate Bill");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayProducts();
                    System.out.print("Enter product ID to add to cart: ");
                    int productId = scanner.nextInt();
                    Product product = findProductById(productId);
                    if (product != null) {
                        cart.add(product);
                        System.out.println("Product added to cart!");
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;
                case 2:
                    displayCart(cart);
                    break;
                case 3:
                    generateBill(cart);
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

    private static void initializeProducts() {
        products.add(new Product(1, "Milk", 2.5));
        products.add(new Product(2, "Bread", 1.0));
        products.add(new Product(3, "Eggs", 3.0));
        // Add more products here
    }

    private static void displayProducts() {
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static Product findProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    private static void displayCart(List<Product> cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        System.out.println("Cart:");
        for (Product product : cart) {
            System.out.println(product);
        }
    }

    private static void generateBill(List<Product> cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty! Cannot generate bill.");
            return;
        }

        double totalAmount = 0;
        System.out.println("Bill:");
        for (Product product : cart) {
            System.out.println(product);
            totalAmount += product.getPrice();
        }
        System.out.printf("Total Amount: $%.2f%n", totalAmount);
    }
}

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price;
    }
}
