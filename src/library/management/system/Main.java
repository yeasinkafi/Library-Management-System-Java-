package library.management.system;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleIO io = new ConsoleIO(scanner);

        UserFactory userFactory = new UserFactory();
        UserRepository userRepo = new FileUserRepository(userFactory);
        BookRepository bookRepo = new FileBookRepository();
        OrderRepository orderRepo = new FileOrderRepository();
        BorrowingRepository borrowingRepo = new FileBorrowingRepository();

        UserService userService = new UserService(userRepo);
        BookService bookService = new BookService(bookRepo);
        OrderService orderService = new OrderService(orderRepo);
        BorrowingService borrowingService = new BorrowingService(borrowingRepo, bookService);
        DataService dataService = new DataService(userRepo, bookRepo, orderRepo, borrowingRepo);

        AppContext ctx = new AppContext(userService, bookService, orderService, borrowingService, dataService, io);

        while (true) {
            showWelcomeMenu();
            int choice = io.readIntInRange("Choose option: ", 0, 2);
            if (choice == 0) {
                System.out.println("Goodbye!");
                return;
            }
            if (choice == 1) {
                login(ctx);
            } else {
                createNewUser(ctx);
            }
        }
    }

    private static void showWelcomeMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("0. Exit");
        System.out.println("1. Login");
        System.out.println("2. New User");
    }

    private static void login(AppContext ctx) {
        ConsoleIO io = ctx.io();
        String phone = io.readLine("Enter phone number: ");
        String email = io.readLine("Enter email: ");
        String password = io.readLine("Enter password: ");

        LoginRequest request = new LoginRequest(phone, email, password);
        User user = ctx.userService().login(request).orElse(null);
        if (user == null) {
            System.out.println("Invalid credentials!");
            return;
        }
        user.menu(ctx);
    }

    private static void createNewUser(AppContext ctx) {
        ConsoleIO io = ctx.io();

        System.out.println("\n=== Create New User ===");
        String name = io.readLine("Name: ");
        String phone = io.readLine("Phone Number: ");
        String email = io.readLine("Email: ");
        String password = io.readLine("Password: ");
        UserType type = readUserType(io);

        UserRegistration registration = new UserRegistration(name, phone, email, password, type);
        ctx.userService().registerUser(registration);
        System.out.println("User created. You can login now.");
    }

    private static UserType readUserType(ConsoleIO io) {
        while (true) {
            String userTypeText = io.readLine("User Type (Admin/Normal User): ");
            if (userTypeText.equalsIgnoreCase("Admin") || userTypeText.equalsIgnoreCase("Normal User")) {
                return UserType.fromUserInput(userTypeText);
            }
            System.out.println("Please type Admin or Normal User.");
        }
    }
}
