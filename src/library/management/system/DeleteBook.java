package library.management.system;

import java.util.Scanner;

public class DeleteBook implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Delete Book ===");
        System.out.print("Enter book name to delete: ");
        String bookname = scanner.nextLine().trim();

        int before = database.getBooks().size();
        database.deleteBook(bookname);
        int after = database.getBooks().size();

        if (after < before) {
            database.saveBooks();
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }
}
