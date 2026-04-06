package library.management.system;

public interface IOOperation {
    String label();
    void oper(AppContext ctx, User user);
}
