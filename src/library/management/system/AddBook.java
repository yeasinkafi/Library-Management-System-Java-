package library.management.system;

import java.util.Scanner;

public class AddBook implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Add Book ===");

        System.out.print("Book Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Publisher: ");
        String publisher = scanner.nextLine().trim();

        int qty = readInt(scanner, "Quantity: ", 0, Integer.MAX_VALUE);
        int brwCopies = readInt(scanner, "Borrowable Copies: ", 0, Integer.MAX_VALUE);
        double price = readDouble(scanner, "Price: ", 0.0, Double.MAX_VALUE);

        Book book = new Book(name, author, publisher, qty, brwCopies, price);
        database.addBook(book);
        database.saveBooks();

        System.out.println("Book added successfully!");
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

    private double readDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            try {
                double v = Double.parseDouble(in);
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
