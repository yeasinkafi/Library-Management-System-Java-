package library.management.system;

import java.util.Scanner;

public class PlaceOrder implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Place Order ===");

        System.out.print("Enter book name: ");
        String bookname = scanner.nextLine().trim();

        Book found = database.findBookByName(bookname);

        if (found == null) {
            System.out.println("Book not found!");
            return;
        }

        int qty = readInt(scanner, "Enter quantity to order: ", 1, Integer.MAX_VALUE);

        
        if (found.getQty() < qty) {
            System.out.println("Warning: Current stock is only " + found.getQty()
                    + ". Admin may reject if stock isn't available.");
        }

        Order order = new Order(found.getName(), qty, user.getName(), user.getPhoneNumber());
        database.addOrder(order);
        database.saveOrders();

        System.out.println("Order placed successfully!");
        System.out.println("Status: " + order.getStatus() + " (Waiting for admin confirmation)");
    }

    private int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(in);
                if (v < min || v > max) {
                    System.out.println("Enter value between " + min + " and " + max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}
