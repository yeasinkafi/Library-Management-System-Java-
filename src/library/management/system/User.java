package library.management.system;

public abstract class User {
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final String password;

    protected IOOperation[] operations;

    public User(String name, String phoneNumber, String email, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public void menu(AppContext ctx) {
        ConsoleIO io = ctx.io();
        while (true) {
            printMenuHeader();
            System.out.println("0. Logout");
            for (int i = 0; i < operations.length; i++) {
                System.out.println((i + 1) + ". " + operations[i].label());
            }

            int choice = io.readIntInRange("Choose option: ", 0, operations.length);
            if (choice == 0) return;

            operations[choice - 1].oper(ctx, this);
        }
    }

    protected abstract void printMenuHeader();
    public abstract UserType getUserType();

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
