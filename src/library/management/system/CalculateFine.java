package library.management.system;

import java.util.Scanner;

public class CalculateFine implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Calculate Fine ===");
        if (database.getBorrowings().isEmpty()) {
            System.out.println("No borrowings found.");
            return;
        }

        boolean any = false;
        for (Borrowing b : database.getBorrowings()) {
            long daysLeft = b.getDaysLeft();
            if (daysLeft < 0) {
                any = true;
                long overdueDays = -daysLeft;
                long fine = overdueDays * 10;
                System.out.println("-----------------------------------");
                System.out.println("User: " + b.getUserName());
                System.out.println("Phone: " + b.getPhoneNumber());
                System.out.println("Book: " + b.getBookName());
                System.out.println("Overdue days: " + overdueDays);
                System.out.println("Fine: " + fine);
            }
        }
        if (!any) {
            System.out.println("No overdue borrowings.");
        } else {
            System.out.println("-----------------------------------");
        }
    }
}
