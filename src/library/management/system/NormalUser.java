package library.management.system;

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
    protected void printMenuHeader() {
        System.out.println("\n=== User Menu ===");
    }

    @Override
    public UserType getUserType() {
        return UserType.NORMAL_USER;
    }
}
