package library.management.system;

import java.util.ArrayList;
import java.util.Scanner;

public class MyOrders implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== My Orders ===");

        ArrayList<Order> all = database.getOrders();
        ArrayList<Integer> myIdx = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            Order o = all.get(i);
            if (o.getUsername().equalsIgnoreCase(user.getName())
                    && o.getPhonenumber().equals(user.getPhoneNumber())) {
                myIdx.add(i);
            }
        }

        if (myIdx.isEmpty()) {
            System.out.println("You have no orders.");
            return;
        }

        printOrders(all, myIdx);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("0. Back");
            System.out.println("1. Cancel a pending order");
            int choice = readInt(scanner, "Choose: ", 0, 1);

            if (choice == 0) return;

            if (choice == 1) {
                int orderNo = readInt(scanner, "Enter order number to cancel: ", 1, myIdx.size());
                int realIndex = myIdx.get(orderNo - 1);
                Order o = all.get(realIndex);

                if (o.getStatus() != Order.Status.PENDING) {
                    System.out.println("Only PENDING orders can be cancelled. This order is: " + o.getStatus());
                    continue;
                }

                o.setStatus(Order.Status.CANCELLED);
                database.saveOrders();
                System.out.println("Order cancelled.");

                // refresh list
                printOrders(all, myIdx);
            }
        }
    }

    private void printOrders(ArrayList<Order> all, ArrayList<Integer> myIdx) {
        System.out.println("-----------------------------------");
        for (int j = 0; j < myIdx.size(); j++) {
            Order o = all.get(myIdx.get(j));
            System.out.println((j + 1) + ") Book: " + o.getBookname()
                    + " | Qty: " + o.getQty()
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
