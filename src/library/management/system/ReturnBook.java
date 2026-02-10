package library.management.system;

import java.util.Iterator;
import java.util.Scanner;

public class ReturnBook implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Return Book ===");
        System.out.print("Enter book name: ");
        String bookname = scanner.nextLine().trim();

        boolean foundBorrow = false;

        Iterator<Borrowing> it = database.getBorrowings().iterator();
        while (it.hasNext()) {
            Borrowing b = it.next();
            if (b.getUserName().equalsIgnoreCase(user.getName())
                    && b.getPhoneNumber().equals(user.getPhoneNumber())
                    && b.getBookName().equalsIgnoreCase(bookname)) {

                foundBorrow = true;

                
                Book book = database.findBookByName(bookname);
                if (book != null) {
                    book.setBrwcopies(book.getBrwcopies() + 1);
                    database.saveBooks();
                }

                long daysLeft = b.getDaysLeft();
                if (daysLeft < 0) {
                    long overdueDays = -daysLeft;
                    System.out.println("Book is overdue by " + overdueDays + " day(s).");
                    System.out.println("Fine (10 per day): " + (overdueDays * 10));
                } else {
                    System.out.println("Returned on time. Days left: " + daysLeft);
                }

                it.remove();
                database.saveBorrowings();
                System.out.println("Return successful!");
                break;
            }
        }

        if (!foundBorrow) {
            System.out.println("You didn't borrow this book!");
        }
    }
}
