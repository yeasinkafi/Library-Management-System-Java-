package library.management.system;

import java.util.ArrayList;
import java.util.Scanner;

public class ViewOrders implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Orders ===");

        ArrayList<Order> orders = database.getOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        printAllOrders(orders);

        
        if (!(user instanceof Admin)) {
            return;
        }

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("0. Back");
            System.out.println("1. Confirm an order");
            System.out.println("2. Reject an order");

            int choice = readInt(scanner, "Choose: ", 0, 2);
            if (choice == 0) return;

            int orderNo = readInt(scanner, "Enter order number: ", 1, orders.size());
            Order selected = orders.get(orderNo - 1);

            if (choice == 1) {
                if (selected.getStatus() != Order.Status.PENDING) {
                    System.out.println("Only PENDING orders can be confirmed. This order is: " + selected.getStatus());
                    continue;
                }

                boolean ok = database.confirmOrder(selected);
                if (!ok) {
                    Book b = database.findBookByName(selected.getBookname());
                    if (b == null) {
                        System.out.println("Cannot confirm: book not found in inventory.");
                    } else if (b.getQty() < selected.getQty()) {
                        System.out.println("Cannot confirm: not enough stock. Available: " + b.getQty());
                    } else {
                        System.out.println("Cannot confirm: unknown error.");
                    }
                } else {
                    System.out.println("Order confirmed!");
                }
            } else if (choice == 2) {
                if (selected.getStatus() != Order.Status.PENDING) {
                    System.out.println("Only PENDING orders can be rejected. This order is: " + selected.getStatus());
                    continue;
                }

                boolean ok = database.rejectOrder(selected);
                if (ok) System.out.println("Order rejected!");
                else System.out.println("Cannot reject this order.");
            }

            printAllOrders(orders);
        }
    }

    private void printAllOrders(ArrayList<Order> orders) {
        System.out.println("-----------------------------------");
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.println((i + 1) + ") Book: " + o.getBookname()
                    + " | Qty: " + o.getQty()
                    + " | User: " + o.getUsername()
                    + " | Phone: " + o.getPhonenumber()
                    + " | Date: " + o.getCreatedDate()
                    + " | Status: " + o.getStatus());
        }
        System.out.println("-----------------------------------");
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
