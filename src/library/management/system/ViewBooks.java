package library.management.system;

import java.util.List;

public class ViewBooks implements IOOperation {
    @Override
    public String label() {
        return "View Books";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        List<Book> books = ctx.bookService().listBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.println("\n--- Books ---");
        for (Book b : books) {
            System.out.println("Name: " + b.getName());
            System.out.println("Author: " + b.getAuthor());
            System.out.println("Publisher: " + b.getPublisher());
            System.out.println("Quantity: " + b.getQty());
            System.out.println("Borrow Copies: " + b.getBrwcopies());
            System.out.println("Price: " + b.getPrice());
            System.out.println();
        }
    }
}
