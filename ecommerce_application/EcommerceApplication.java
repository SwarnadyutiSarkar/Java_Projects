import java.util.*;

public class EcommerceApplication {
    private static List<Product> products = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        initializeProducts();

        while (true) {
            System.out.println("\nE-commerce Application");
            System.out.println("1. Browse Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. Manage Inventory");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    browseProducts();
                    break;
                case 2:
                    addToCart(scanner);
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    placeOrder(scanner);
                    break;
                case 5:
                    manageInventory(scanner);
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

    private static void initializeProducts() {
        products.add(new Product(1, "Laptop", 1000.0, 50));
        products.add(new Product(2, "Phone", 700.0, 100));
        products.add(new Product(3, "Tablet", 300.0, 80));
    }

    private static void browseProducts() {
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void addToCart(Scanner scanner) {
        System.out.print("Enter product ID to add to cart: ");
        int productId = scanner.nextInt();
        Product product = findProductById(productId);
        if (product != null) {
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            Cart.addToCart(product, quantity);
            System.out.println("Product added to cart!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void viewCart() {
        System.out.println("Cart:");
        for (CartItem item : Cart.getCartItems()) {
            System.out.println(item);
        }
    }

    private static void placeOrder(Scanner scanner) {
        if (Cart.getCartItems().isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        System.out.print("Enter shipping address: ");
        String address = scanner.nextLine();
        Order order = new Order(Cart.getCartItems(), address);
        orders.add(order);
        System.out.println("Order placed successfully! Order ID: " + order.getOrderId());
        Cart.clearCart();
    }

    private static void manageInventory(Scanner scanner) {
        System.out.println("Manage Inventory:");
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addProduct(scanner);
                break;
            case 2:
                removeProduct(scanner);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        Product product = new Product(products.size() + 1, name, price, quantity);
        products.add(product);
        System.out.println("Product added successfully!");
    }

    private static void removeProduct(Scanner scanner) {
        System.out.print("Enter product ID to remove: ");
        int productId = scanner.nextInt();
        Product product = findProductById(productId);
        if (product != null) {
            products.remove(product);
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static Product findProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
}

class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Quantity: " + quantity;
    }
}

class Cart {
    private static List<CartItem> cartItems = new ArrayList<>();

    public static void addToCart(Product product, int quantity) {
        CartItem item = new CartItem(product, quantity);
        cartItems.add(item);
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void clearCart() {
        cartItems.clear();
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " - Quantity: " + quantity + " - Total Price: $" + (product.getPrice() * quantity);
    }
}

class Order {
    private static int orderIdCounter = 1;
    private int orderId;
    private List<CartItem> cartItems;
    private String shippingAddress;

    public Order(List<CartItem> cartItems, String shippingAddress) {
        this.orderId = orderIdCounter++;
        this.cartItems = cartItems;
        this.shippingAddress = shippingAddress;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        for (CartItem item : cartItems) {
            sb.append(item).append("\n");
        }
        sb.append("Shipping Address: ").append(shippingAddress);
        return sb.toString();
    }
}
