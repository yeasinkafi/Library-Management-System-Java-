package library.management.system;

import java.util.Scanner;

public class ViewBooks implements IOOperation {
    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Books ===");
        if (database.getBooks().isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book book : database.getBooks()) {
            System.out.println("-----------------------------------");
            System.out.println("Name: " + book.getName());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Publisher: " + book.getPublisher());
            System.out.println("Quantity (for sell): " + book.getQty());
            System.out.println("Borrowable Copies: " + book.getBrwcopies());
            System.out.println("Price: " + book.getPrice());
        }
        System.out.println("-----------------------------------");
    }
}
