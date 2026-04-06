package library.management.system;

import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Database database = new Database();
        database.readUsers();
        database.readBooks();
        database.readOrders();
        database.readBorrowings();

        int num;
        do {
            System.out.println("\n=== Library Management System ===");
            System.out.println("0. Exit");
            System.out.println("1. Login");
            System.out.println("2. New User");

            num = readIntInRange("Choose option: ", 0, 2);

            switch (num) {
                case 1:
                    login(database);
                    break;
                case 2:
                    newUser(database);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
            }
        } while (num != 0);
    }

    private static void login(Database database) {
        System.out.print("Enter phone number: ");
        String phonenumber = SCANNER.nextLine().trim();

        System.out.print("Enter email: ");
        String email = SCANNER.nextLine().trim();

        System.out.print("Enter password: ");
        String password = SCANNER.nextLine().trim();

        User user = database.login(phonenumber, email, password);
        if (user == null) {
            System.out.println("Invalid credentials!");
            return;
        }
        user.menu(database, SCANNER);
    }

    private static void newUser(Database database) {
        System.out.println("\n=== Create New User ===");
        System.out.print("Name: ");
        String name = SCANNER.nextLine().trim();

        System.out.print("Phone Number: ");
        String phoneNumber = SCANNER.nextLine().trim();

        System.out.print("Email: ");
        String email = SCANNER.nextLine().trim();

        System.out.print("Password: ");
        String password = SCANNER.nextLine().trim();

        String userType;
        while (true) {
            System.out.print("User Type (Admin/Normal User): ");
            userType = SCANNER.nextLine().trim();
            if (userType.equalsIgnoreCase("Admin") || userType.equalsIgnoreCase("Normal User")) {
                break;
            }
            System.out.println("Invalid type. Please enter 'Admin' or 'Normal User'.");
        }

        User user;
        if (userType.equalsIgnoreCase("Admin")) {
            user = new Admin(name, phoneNumber, email, password);
        } else {
            user = new NormalUser(name, phoneNumber, email, password);
        }

        database.addUser(user);
        database.saveUsers();
        System.out.println("User created successfully!");
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
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
