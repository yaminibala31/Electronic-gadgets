package entity;

public class Product {
    private int productId;
    private String productName;
    private String description;
    private double price;

    public Product(int productId, String productName, String description, double price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        setPrice(price);
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price must be non-negative.");
        this.price = price;
    }

    public void updateProductInfo(String description, double price) {
        this.description = description;
        setPrice(price);
    }

    public void getProductDetails() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
    }
}
