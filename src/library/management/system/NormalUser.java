package library.management.system;

import java.util.Scanner;

public class NormalUser extends User {

    public NormalUser(String name, String phoneNumber, String email, String password) {
        super(name, phoneNumber, email, password);
        this.operations = new IOOperation[] {
                new ViewBooks(),
                new SearchBook(),
                new BorrowBook(),
                new ReturnBook(),
                new PlaceOrder(),
                new MyOrders(),
                new Exit()
        };
    }

    @Override
    public void menu(Database database, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== User Menu ===");
            System.out.println("0. Logout");
            System.out.println("1. View Books");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Place Order");
            System.out.println("6. My Orders (Cancel pending)");
            System.out.println("7. Exit Program");

            choice = readIntInRange(scanner, "Choose option: ", 0, operations.length);

            if (choice == 0) {
                System.out.println("Logged out.");
                break;
            }

            operations[choice - 1].oper(database, this, scanner);
        } while (true);
    }

    private int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val < min || val > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}
