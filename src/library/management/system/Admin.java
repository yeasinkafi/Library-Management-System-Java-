package library.management.system;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String name, String phoneNumber, String email, String password) {
        super(name, phoneNumber, email, password);
        this.operations = new IOOperation[] {
                new ViewBooks(),
                new AddBook(),
                new DeleteBook(),
                new ViewOrders(),      
                new CalculateFine(),
                new DeleteAllData(),
                new Exit()
        };
    }

    @Override
    public void menu(Database database, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("0. Logout");
            System.out.println("1. View Books");
            System.out.println("2. Add Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View Orders (Confirm/Reject)");
            System.out.println("5. Calculate Fine");
            System.out.println("6. Delete All Data");
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
