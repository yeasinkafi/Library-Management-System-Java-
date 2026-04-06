package library.management.system;

public class BorrowBook implements IOOperation {

    @Override
    public String label() {
        return "Borrow Book";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        String bookName = ctx.io().readLine("Book name to borrow: ");
        boolean ok = ctx.borrowingService().borrowBook(bookName, user);
        if (ok) System.out.println("Borrowed successfully.");
        else System.out.println("Cannot borrow. Maybe book not found or no borrow copies left.");
    }
}
