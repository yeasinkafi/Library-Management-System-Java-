package library.management.system;

import java.time.LocalDate;
import java.util.Scanner;

public class BorrowBook implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Borrow Book ===");
        System.out.print("Enter book name: ");
        String bookname = scanner.nextLine().trim();

        Book found = database.findBookByName(bookname);

        if (found == null) {
            System.out.println("Book not found!");
            return;
        }

        if (found.getBrwcopies() <= 0) {
            System.out.println("No borrowable copies available for this book.");
            return;
        }

        // Borrow for 14 days
        LocalDate start = LocalDate.now();
        LocalDate finish = start.plusDays(14);

        found.setBrwcopies(found.getBrwcopies() - 1);
        database.saveBooks();

        Borrowing borrowing = new Borrowing(found.getName(), user.getName(), user.getPhoneNumber(), start, finish);
        database.addBorrowing(borrowing);
        database.saveBorrowings();

        System.out.println("Borrowed successfully!");
        System.out.println("Return by: " + finish);
    }
}
