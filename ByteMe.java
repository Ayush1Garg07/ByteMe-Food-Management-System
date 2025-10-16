package Project;
import java.util.*;

class MenuItem {
    private int id;
    private String name;
    private double price;
    private boolean available;
    private List<Review> reviews;

    public MenuItem(int id, String name, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
        this.reviews = new ArrayList<>();
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void displayReviews() {
        System.out.println("Reviews for " + name + ":");
        for (Review review : reviews) {
            System.out.println(review);
        }
    }

    @Override
    public String toString() {
        return String.format("%d. %s - \u20B9%.2f (%s)", id, name, price, available ? "Available" : "Not Available");
    }
}

class Review {
    private String comment;
    private int rating;

    public Review(String comment, int rating) {
        this.comment = comment;
        this.rating =8 rating;
    }

    @Override
    public String toString() {
        return String.format("Rating: %d - %s", rating, comment);
    }
}

class Order {
    private static int orderCounter = 0;
    private int orderId;
    private String studentName;
    private List<MenuItem> orderItems;
    private String status;
    private Date timestamp;
    private String specialRequest;
    private boolean isVIP;

    public Order(String studentName) {
        this.orderId = ++orderCounter;
        this.studentName = studentName;
        this.orderItems = new ArrayList<>();
        this.status = "Ordered";
        this.timestamp = new Date();
    }

    public void addItem(MenuItem item) {
        orderItems.add(item);
    }

    public void removeItem(MenuItem item) {
        orderItems.remove(item);
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public List<MenuItem> getOrderItems() {
        return orderItems;
    }

    public String getStatus() {
        return status;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setSpecialRequest(String request) {
        this.specialRequest = request;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }

    public boolean isVIP() {
        return isVIP;
    }

    @Override
    public String toString() {
        return String.format("Order ID: %d | Student: %s | Status: %s | Items: %s | Special Request: %s | VIP: %s", 
            orderId, studentName, status, orderItems, specialRequest, isVIP);
    }
}

class Customer {
    private String name;
    private String hostelRoomNumber;
    private boolean isVIP;
    private Map<MenuItem, Integer> cart;
    private List<Order> orderHistory;
    private double balance;

    public Customer(String name, String hostelRoomNumber) {
        this.name = name;
        this.hostelRoomNumber = hostelRoomNumber;
        this.cart = new HashMap<>();
        this.orderHistory = new ArrayList<>();
        this.balance = 0.0; // Initial balance
        this.isVIP = false;
    }

    public void becomeVIP(double fee) {
        if (balance >= fee) {
            balance -= fee;
            isVIP = true;
            System.out.println(name + " is now a VIP!");
        } else {
            System.out.println("Insufficient funds to become VIP.");
        }
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void addToCart(MenuItem item, int quantity) {
        cart.put(item, quantity);
    }

    public void modifyQuantity(MenuItem item, int newQuantity) {
        if (cart.containsKey(item)) {
            cart.put(item, newQuantity);
        }
    }

    public void removeFromCart(MenuItem item) {
        cart.remove(item);
    }

    public double viewCartTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : cart.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void checkout(CanteenSystem system) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }
        Order order = new Order(name);
        for (Map.Entry<MenuItem, Integer> entry : cart.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                order.addItem(entry.getKey());
            }
        }
        system.placeOrder(order);
        orderHistory.add(order);
        cart.clear();
        System.out.println("Checkout complete!");
    }

    public void viewOrderHistory() {
        System.out.println("Order History:");
        for (Order order : orderHistory) {
            System.out.println(order);
        }
    }

    public void viewMenu(CanteenSystem system) {
        system.displayMenu();
    }

    public void searchMenu(CanteenSystem system, String keyword) {
        system.searchMenu(keyword);
    }

    public void filterMenuByCategory(CanteenSystem system, String category) {
        system.filterMenuByCategory(category);
    }

    public void sortMenuByPrice(CanteenSystem system, boolean ascending) {
        system.sortMenuByPrice(ascending);
    }

    public void viewOrderStatus(int orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId() == orderId) {
                System.out.println(order);
                return;
            }
        }
        System.out.println("Order not found.");
    }

    public void cancelOrder(int orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId() == orderId && order.getStatus().equals("Ordered")) {
                order.updateStatus("Canceled");
                System.out.println("Order canceled: " + orderId);
                return;
            }
        }
        System.out.println("Order cannot be canceled or not found.");
    }

    public void provideReview(MenuItem item, String review, int rating) {
        item.addReview(new Review(review, rating));
    }

    public void viewReviews(MenuItem item) {
        item.displayReviews();
    }
}

class CanteenStaff {
    private String staffId;
    private String name;

    public CanteenStaff(String staffId, String name) {
        this.staffId = staffId;
        this.name = name;
    }

    public void addMenuItem(CanteenSystem system, MenuItem item) {
        system.addMenuItem(item);
    }

    public void removeMenuItem(CanteenSystem system, int itemId) {
        system.removeMenuItem(itemId);
    }

    public void updateMenuItem(CanteenSystem system, int itemId, MenuItem updatedItem) {
        system.updateMenuItem(itemId, updatedItem);
    }

    public void viewOrders(CanteenSystem system) {
        system.viewOrders();
    }

    public void updateOrderStatus(CanteenSystem system, int orderId, String status) {
        system.updateOrderStatus(orderId, status);
    }
}

class Admin extends CanteenStaff {
    public Admin(String staffId, String name) {
        super(staffId, name);
    }

    public void generateDailySalesReport(CanteenSystem system) {
        system.generateDailySalesReport();
    }
}

class CanteenSystem {
    private Map<Integer, MenuItem> menuItems;
    private List<Order> orders;

    public CanteenSystem() {
        this.menuItems = new HashMap<>();
        this.orders = new ArrayList<>();
        initializeMenu();
    }

    private void initializeMenu() {
        addMenuItem(new MenuItem(1, "Aloo Parantha", 30.0, true));
        addMenuItem(new MenuItem(2, "Maggi", 25.0, true));
        addMenuItem(new MenuItem(3, "Egg Maggi", 40.0, true));
        addMenuItem(new MenuItem(4, "Paneer Parantha", 40.0, true));
        addMenuItem(new MenuItem(5, "Chowmein", 45.0, true));
        addMenuItem(new MenuItem(6, "Veg Roll", 40.0, true));
        addMenuItem(new MenuItem(7, "Egg Roll", 60.0, true));
        addMenuItem(new MenuItem(8, "Chicken Roll", 80.0, true));
        addMenuItem(new MenuItem(9, "Tea", 10.0, true));
        addMenuItem(new MenuItem(10, "Coffee", 20.0, true));
        addMenuItem(new MenuItem(11, "Cold Coffee", 40.0, true));
        addMenuItem(new MenuItem(12, "Orange Juice", 50.0, true));
        addMenuItem(new MenuItem(13, "Lunch Thali", 80.0, true));
        addMenuItem(new MenuItem(14, "Dinner Thali", 100.0, true));
    }

    public void addMenuItem(MenuItem item) {
        menuItems.put(item.getId(), item);
        System.out.println("Menu item added: " + item);
    }

    public void removeMenuItem(int itemId) {
        if (menuItems.remove(itemId) != null) {
            System.out.println("Menu item with ID " + itemId + " has been removed.");
            // Update pending orders
            for (Order order : orders) {
                for (MenuItem item : order.getOrderItems()) {
                    if (item.getId() == itemId) {
                        order.updateStatus("Denied");
                    }
                }
            }
        } else {
            System.out.println("Menu item not found.");
        }
    }

    public void updateMenuItem(int itemId, MenuItem updatedItem) {
        if (menuItems.containsKey(itemId)) {
            menuItems.put(itemId, updatedItem);
            System.out.println("Menu item updated: " + updatedItem);
        } else {
            System.out.println("Menu item not found.");
        }
    }

    public void displayMenu() {
        System.out.println("---- Canteen Menu ----");
        for (MenuItem item : menuItems.values()) {
            System.out.println(item);
        }
    }

    public void searchMenu(String keyword) {
        System.out.println("---- Search Results ----");
        for (MenuItem item : menuItems.values()) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(item);
            }
        }
    }

    public void filterMenuByCategory(String category) {
        // Assuming category is part of MenuItem - This requires modification in MenuItem class
        System.out.println("---- Filtered Results ----");
        // Placeholder for category check
        // for (MenuItem item : menuItems.values()) {
        //     if (item.getCategory().equalsIgnoreCase(category)) {
        //         System.out.println(item);
        //     }
        // }
    }

    public void sortMenuByPrice(boolean ascending) {
        List<MenuItem> sortedMenu = new ArrayList<>(menuItems.values());
        sortedMenu.sort(Comparator.comparingDouble(MenuItem::getPrice));
        if (!ascending) {
            Collections.reverse(sortedMenu);
        }
        System.out.println("---- Sorted Menu ----");
        for (MenuItem item : sortedMenu) {
            System.out.println(item);
        }
    }

    public void placeOrder(Order order) {
        orders.add(order);
        System.out.println("Order placed: " + order);
    }

    public void viewOrders() {
        System.out.println("---- Current Orders ----");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                order.updateStatus(status);
                System.out.println("Order status updated: " + order);
                return;
            }
        }
        System.out.println("Order not found.");
    }

    public void generateDailySalesReport() {
        double totalSales = 0.0;
        Map<String, Integer> itemCounts = new HashMap<>();
        int totalOrders = 0;

        for (Order order : orders) {
            if (order.getStatus().equals("Completed")) {
                totalSales += order.getOrderItems().stream().mapToDouble(MenuItem::getPrice).sum();
                totalOrders++;
                for (MenuItem item : order.getOrderItems()) {
                    itemCounts.put(item.getName(), itemCounts.getOrDefault(item.getName(), 0) + 1);
                }
            }
        }

        System.out.println("---- Daily Sales Report ----");
        System.out.printf("Total Sales: \u20B9%.2f\n", totalSales);
        System.out.println("Total Orders: " + totalOrders);
        System.out.println("Most Popular Items:");
        itemCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("%s: %d\n", entry.getKey(), entry.getValue()));
    }

    // Getter methods for accessing menu items and orders
    public Map<Integer, MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<Order> getOrders() {
        return orders;
    }
}

public class ByteMe {
    private static CanteenSystem canteenSystem = new CanteenSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to Byte Me! Please choose an option:");
            System.out.println("1. Customer Menu");
            System.out.println("2. Canteen Staff Menu");
            System.out.println("3. Admin Menu");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                    canteenStaffMenu();
                    break;
                case 3:
                    adminMenu();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void customerMenu() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your hostel room number:");
        String hostelRoomNumber = scanner.nextLine();
        Customer customer = new Customer(name, hostelRoomNumber);

        while (true) {
            System.out.println("\n---- Customer Menu ----");
            System.out.println("1. View Menu");
            System.out.println("2. Search Menu");
            System.out.println("3. Filter Menu by Category");
            System.out.println("4. Sort Menu by Price");
            System.out.println("5. Add to Cart");
            System.out.println("6. Checkout");
            System.out.println("7. View Order History");
            System.out.println("8. View Order Status");
            System.out.println("9. Cancel Order");
            System.out.println("10. Provide Review");
            System.out.println("11. Exit to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    customer.viewMenu(canteenSystem);
                    break;
                case 2:
                    System.out.println("Enter keyword to search:");
                    String keyword = scanner.nextLine();
                    customer.searchMenu(canteenSystem, keyword);
                    break;
                case 3:
                    System.out.println("Enter category to filter:");
                    String category = scanner.nextLine();
                    customer.filterMenuByCategory(canteenSystem, category);
                    break;
                case 4:
                    System.out.println("Sort ascending? (yes/no):");
                    boolean ascending = scanner.nextLine().equalsIgnoreCase("yes");
                    customer.sortMenuByPrice(canteenSystem, ascending);
                    break;
                case 5:
                    System.out.println("Enter menu item ID to add to cart:");
                    int itemId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    MenuItem itemToAdd = canteenSystem.getMenuItems().get(itemId);
                    if (itemToAdd != null) {
                        System.out.println("Enter quantity:");
                        int quantity = scanner.nextInt();
                        customer.addToCart(itemToAdd, quantity);
                        System.out.println(itemToAdd.getName() + " added to cart.");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 6:
                    customer.checkout(canteenSystem);
                    break;
                case 7:
                    customer.viewOrderHistory();
                    break;
                case 8:
                    System.out.println("Enter Order ID to view status:");
                    int orderId = scanner.nextInt();
                    customer.viewOrderStatus(orderId);
                    break;
                case 9:
                    System.out.println("Enter Order ID to cancel:");
                    int cancelId = scanner.nextInt();
                    customer.cancelOrder(cancelId);
                    break;
                case 10:
                    System.out.println("Enter menu item ID to review:");
                    int reviewItemId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    MenuItem itemToReview = canteenSystem.getMenuItems().get(reviewItemId);
                    if (itemToReview != null) {
                        System.out.println("Enter your review:");
                        String review = scanner.nextLine();
                        System.out.println("Enter your rating (1 to 5):");
                        int rating = scanner.nextInt();
                        customer.provideReview(itemToReview, review, rating);
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case 11:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void canteenStaffMenu() {
        CanteenStaff staff = new CanteenStaff("001", "John Doe"); // Example staff
        while (true) {
            System.out.println("\n---- Canteen Staff Menu ----");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Menu Item");
            System.out.println("4. View Orders");
            System.out.println("5. Update Order Status");
            System.out.println("6. Exit to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    addMenuItem(staff);
                    break;
                case 2:
                    removeMenuItem(staff);
                    break;
                case 3:
                    updateMenuItem(staff);
                    break;
                case 4:
                    staff.viewOrders(canteenSystem);
                    break;
                case 5:
                    updateOrderStatus(staff);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminMenu() {
        Admin admin = new Admin("002", "Admin User"); // Example admin
        while (true) {
            System.out.println("\n---- Admin Menu ----");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Update Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. View Pending Orders");
            System.out.println("5. Update Order Status");
            System.out.println("6. Generate Daily Sales Report");
            System.out.println("7. Exit to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    addMenuItem(admin);
                    break;
                case 2:
                    updateMenuItem(admin);
                    break;
                case 3:
                    removeMenuItem(admin);
                    break;
                case 4:
                    viewPendingOrders(admin);
                    break;
                case 5:
                    updateOrderStatus(admin);
                    break;
                case 6:
                    admin.generateDailySalesReport(canteenSystem);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addMenuItem(CanteenStaff staff) {
        System.out.println("Enter item ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.println("Enter item name:");
        String name = scanner.nextLine();
        System.out.println("Enter item price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline
        System.out.println("Is the item available? (true/false):");
        boolean available = scanner.nextBoolean();
        scanner.nextLine(); // Consume the newline

        staff.addMenuItem(canteenSystem, new MenuItem(id, name, price, available));
    }

    private static void removeMenuItem(CanteenStaff staff) {
        System.out.println("Enter item ID to remove:");
        int itemId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        staff.removeMenuItem(canteenSystem, itemId);
    }

    private static void updateMenuItem(CanteenStaff staff) {
        System.out.println("Enter item ID to update:");
        int itemId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.println("Enter new item name:");
        String name = scanner.nextLine();
        System.out.println("Enter new item price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline
        System.out.println("Is the item available? (true/false):");
        boolean available = scanner.nextBoolean();
        scanner.nextLine(); // Consume the newline

        staff.updateMenuItem(canteenSystem, itemId, new MenuItem(itemId, name, price, available));
    }

    private static void updateOrderStatus(CanteenStaff staff) {
        System.out.println("Enter Order ID to update status:");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.println("Enter new status:");
        String status = scanner.nextLine();
        staff.updateOrderStatus(canteenSystem, orderId, status);
    }

    private static void viewPendingOrders(Admin admin) {
        System.out.println("---- Pending Orders ----");
        for (Order order : canteenSystem.getOrders()) {
            if (order.getStatus().equals("Ordered")) {
                System.out.println(order);
            }
        }
    }
}
