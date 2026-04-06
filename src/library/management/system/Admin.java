package library.management.system;

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
    protected void printMenuHeader() {
        System.out.println("\n=== Admin Menu ===");
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}
