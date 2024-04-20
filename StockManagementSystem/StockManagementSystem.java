import java.util.*;

public class StockManagementSystem {
    private Map<String, Product> products;
    private Map<String, Order> orders;

    public StockManagementSystem() {
        this.products = new HashMap<>();
        this.orders = new HashMap<>();
    }

    public void addProduct(String productId, String productName, double price, int quantity) {
        if (!products.containsKey(productId)) {
            Product product = new Product(productId, productName, price, quantity);
            products.put(productId, product);
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Product already exists.");
        }
    }

    public void updateProduct(String productId, String productName, double price, int quantity) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            product.setProductName(productName);
            product.setPrice(price);
            product.setQuantity(quantity);
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void deleteProduct(String productId) {
        if (products.containsKey(productId)) {
            products.remove(productId);
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void addOrder(String orderId, String productId, int quantity) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            if (product.getQuantity() >= quantity) {
                Order order = new Order(orderId, productId, quantity);
                orders.put(orderId, order);
                product.setQuantity(product.getQuantity() - quantity);
                System.out.println("Order placed successfully.");
            } else {
                System.out.println("Insufficient stock.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    public void displayProducts() {
        System.out.println("Product List:");
        for (Product product : products.values()) {
            System.out.println(product);
        }
    }

    public void displayOrders() {
        System.out.println("Order List:");
        for (Order order : orders.values()) {
            System.out.println(order);
        }
    }

    public static void main(String[] args) {
        StockManagementSystem system = new StockManagementSystem();

        // Add products
        system.addProduct("P001", "Laptop", 1000.0, 10);
        system.addProduct("P002", "Smartphone", 500.0, 20);

        // Update product
        system.updateProduct("P001", "Gaming Laptop", 1200.0, 8);

        // Add order
        system.addOrder("O001", "P001", 2);

        // Display products and orders
        system.displayProducts();
        system.displayOrders();
    }
}

class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantity;

    public Product(String productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

class Order {
    private String orderId;
    private String productId;
    private int quantity;

    public Order(String orderId, String productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
