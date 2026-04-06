package library.management.system;

import java.util.List;

public class SearchBook implements IOOperation {

    @Override
    public String label() {
        return "Search Book";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        ConsoleIO io = ctx.io();

        showSearchMenu();
        int choice = io.readIntInRange("Choose: ", 1, 3);
        String key = io.readLine("Enter search text: ");
        SearchOption option = SearchOption.fromChoice(choice);

        List<Book> result = searchBooks(ctx, option, key);
        if (result.isEmpty()) {
            System.out.println("No match found.");
            return;
        }

        System.out.println("\nFound " + result.size() + " book(s):");
        for (Book b : result) {
            System.out.println("- " + b.getName() + " | " + b.getAuthor() + " | " + b.getPublisher());
        }
    }

    private void showSearchMenu() {
        System.out.println("\n=== Search Book ===");
        System.out.println("1. Book name");
        System.out.println("2. Author");
        System.out.println("3. Publisher");
    }

    private List<Book> searchBooks(AppContext ctx, SearchOption option, String key) {
        if (option == SearchOption.NAME) {
            return ctx.bookService().searchByName(key);
        }
        if (option == SearchOption.AUTHOR) {
            return ctx.bookService().searchByAuthor(key);
        }
        return ctx.bookService().searchByPublisher(key);
    }
}
