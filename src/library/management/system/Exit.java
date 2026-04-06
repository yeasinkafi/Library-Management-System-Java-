package library.management.system;

public class Exit implements IOOperation {
    @Override
    public String label() {
        return "Exit";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        System.out.println("Goodbye.");
        System.exit(0);
    }
}
