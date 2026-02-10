package library.management.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SearchBook implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Search Book ===");
        System.out.println("Search by:");
        System.out.println("1. Book name");
        System.out.println("2. Author");
        System.out.println("3. Publisher");
        System.out.print("Choose option: ");

        int option = readInt(scanner, 1, 3);
        System.out.print("Enter search text: ");
        String q = scanner.nextLine().trim().toLowerCase();

        if (q.isEmpty()) {
            System.out.println("Search text cannot be empty.");
            return;
        }

        List<Book> matches = new ArrayList<>();
        for (Book b : database.getBooks()) {
            String field;
            if (option == 1) field = b.getName();
            else if (option == 2) field = b.getAuthor();
            else field = b.getPublisher();

            if (field != null && field.toLowerCase().contains(q)) {
                matches.add(b);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }

        System.out.println("\nFound " + matches.size() + " book(s):");
        for (Book book : matches) {
            System.out.println("-----------------------------------");
            System.out.println("Name: " + book.getName());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Publisher: " + book.getPublisher());
            System.out.println("Quantity: " + book.getQty());
            System.out.println("Borrowable Copies: " + book.getBrwcopies());
            System.out.println("Price: " + book.getPrice());
        }
        System.out.println("-----------------------------------");
    }

    private int readInt(Scanner scanner, int min, int max) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val < min || val > max) {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
}
