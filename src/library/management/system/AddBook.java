package library.management.system;

public class AddBook implements IOOperation {

    @Override
    public String label() {
        return "Add Book";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        ConsoleIO io = ctx.io();

        String name = io.readLine("Book name: ");
        String author = io.readLine("Author: ");
        String publisher = io.readLine("Publisher: ");
        int qty = io.readInt("Quantity: ");
        int brw = io.readInt("Borrow copies: ");
        double price = io.readDouble("Price: ");

        BookDetails details = new BookDetails(name, author, publisher, qty, brw, price);
        ctx.bookService().addBook(details);
        System.out.println("Book added.");
    }
}
