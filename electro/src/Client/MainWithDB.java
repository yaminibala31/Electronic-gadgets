package Client;

import dao.CustomerDAOImpl;
import dao.OrderDAOImpl;
import entity.Customer;
import entity.Orders;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainWithDB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        OrderDAOImpl orderDAO = new OrderDAOImpl();

        while (true) {
            System.out.println("\n===== TechShop Menu =====");
            System.out.println("1. Register New Customer");
            System.out.println("2. Place New Order");
            System.out.println("3. Get Order by ID");
            System.out.println("4. Get All Orders by Customer ID");
            System.out.println("5. Update Order Status");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
            case 1:
                System.out.print("Enter Customer ID: ");
                int customerId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter First Name: ");
                String firstName = scanner.nextLine();

                System.out.print("Enter Last Name: ");
                String lastName = scanner.nextLine();

                System.out.print("Enter Email: ");
                String email = scanner.nextLine();

                System.out.print("Enter Phone: ");
                String phone = scanner.nextLine();

                System.out.print("Enter Address: ");
                String address = scanner.nextLine();

                try {
                    Customer newCustomer = new Customer(customerId, firstName, lastName, email, phone, address);
                    customerDAO.addCustomer(newCustomer);
                    }
                 catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;


                case 2:
                    System.out.print("Enter Order ID: ");
                    int orderId = scanner.nextInt();
                    System.out.print("Enter Customer ID for Order: ");
                    int custIdForOrder = scanner.nextInt();
                    scanner.nextLine();

                    Customer orderCustomer = new Customer();
                    orderCustomer.setCustomerId(custIdForOrder);

                    System.out.print("Enter Total Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter Order Status: ");
                    String status = scanner.nextLine();

                    Orders newOrder = new Orders(orderId, orderCustomer, LocalDate.now(), amount, status);
                    orderDAO.placeOrder(newOrder);
                    break;

                case 3:
                    System.out.print("Enter Order ID to Fetch: ");
                    int fetchOrderId = scanner.nextInt();
                    Orders fetched = orderDAO.getOrderById(fetchOrderId);
                    if (fetched != null) {
                        System.out.println("Order ID: " + fetched.getOrderId());
                        System.out.println("Order Date: " + fetched.getOrderDate());
                        System.out.println("Amount: " + fetched.getTotalAmount());
                        System.out.println("Status: " + fetched.getOrderStatus());
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Customer ID to Fetch Orders: ");
                    int custId = scanner.nextInt();
                    List<Orders> orders = orderDAO.getOrdersByCustomerId(custId);
                    if (orders.isEmpty()) {
                        System.out.println("No orders found for this customer.");
                    } else {
                        for (Orders order : orders) {
                            System.out.println("OrderID: " + order.getOrderId() +
                                    ", Date: " + order.getOrderDate() +
                                    ", Amount: " + order.getTotalAmount() +
                                    ", Status: " + order.getOrderStatus());
                        }
                    }
                    break;

                case 5:
                    System.out.print("Enter Order ID to Update: ");
                    int updateOrderId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter New Status: ");
                    String newStatus = scanner.nextLine();
                    orderDAO.updateOrderStatus(updateOrderId, newStatus);
                    break;

                case 6:
                    System.out.println("Exiting program...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
