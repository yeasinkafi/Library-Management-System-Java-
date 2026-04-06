package library.management.system;

public class ReturnBook implements IOOperation {

    @Override
    public String label() {
        return "Return Book";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        String bookName = ctx.io().readLine("Book name to return: ");
        boolean ok = ctx.borrowingService().returnBook(bookName, user);
        if (ok) System.out.println("Returned successfully.");
        else System.out.println("Borrow record not found.");
    }
}
