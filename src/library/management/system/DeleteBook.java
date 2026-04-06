package library.management.system;

public class DeleteBook implements IOOperation {
    @Override
    public String label() {
        return "Delete Book";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        String name = ctx.io().readLine("Book name to delete: ");
        boolean ok = ctx.bookService().deleteBook(name);
        if (ok) System.out.println("Book deleted.");
        else System.out.println("Book not found.");
    }
}
