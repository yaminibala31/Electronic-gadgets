package Client;

import entity.*;
import exception.*;
import dao.ShopService;

import java.util.List;
import java.util.Scanner;

public class MainWithoutDB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShopService shopService = new ShopService();

        while (true) {
            System.out.println("\n=== TechShop Menu ===");
            System.out.println("1. Register Customer");
            System.out.println("2. Add Product");
            System.out.println("3. View Products");
            System.out.println("4. Place Order");
            System.out.println("5. View Orders");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1: {
                    System.out.print("Customer ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("First Name: ");
                    String fname = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String lname = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();

                    try {
                        Customer customer = new Customer(id, fname, lname, email, phone, address);
                        System.out.println("Customer registered successfully.");
                        customer.getCustomerDetails();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    System.out.print("Product ID: ");
                    int pid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Product Name: ");
                    String pname = scanner.nextLine();
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();

                    try {
                        Product product = new Product(pid, pname, desc, price);
                        shopService.addProduct(product);
                        System.out.println("Product added successfully.");
                    } catch (InvalidDataException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 3: {
                    System.out.println("\n--- Product List ---");
                    List<Product> productList = shopService.searchProductsByName(""); // all
                    for (Product p : productList) {
                        p.getProductDetails();
                        System.out.println("-----------------");
                    }
                    break;
                }
                case 4: {
                    System.out.print("Order ID: ");
                    int oid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Customer First Name (for dummy search): ");
                    String custName = scanner.nextLine(); 

                    Customer dummyCustomer = new Customer(999, custName, "", "dummy@example.com", "000", "DummyLand");

                    Orders order = new Orders(oid, dummyCustomer);

                    char addMore;
                    do {
                        System.out.print("Product Name to Order: ");
                        String searchName = scanner.nextLine();
                        List<Product> found = shopService.searchProductsByName(searchName);

                        if (found.isEmpty()) {
                            System.out.println("Product not found.");
                            break;
                        }

                        Product p = found.get(0); 
                        System.out.print("Quantity: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();

                        OrderDetails od = new OrderDetails((int) (Math.random() * 1000), order, p, qty);
                        order.addOrderDetail(od);

                        System.out.print("Add more items? (y/n): ");
                        addMore = scanner.nextLine().charAt(0);
                    } while (addMore == 'y' || addMore == 'Y');

                    shopService.addOrder(order);
                    System.out.println("Order placed successfully!");
                    order.getOrderDetails();
                    System.out.println("Total:" + order.getTotalAmount());
                    break;
                }
                case 5: {
                    System.out.println("--- All Orders ---");
                    shopService.sortOrdersByDate();
                    for (Orders order : shopService.getOrders()) {
                        order.getOrderDetails();
                        System.out.println("Total:" + order.getTotalAmount());
                        System.out.println("====================================");
                    }
                    break;
                }
                case 6:
                    System.out.println("Exiting App. Bye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

