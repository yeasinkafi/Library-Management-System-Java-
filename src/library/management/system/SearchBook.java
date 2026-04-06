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

        BookSearchStrategy strategy = BookSearchStrategyFactory.fromChoice(choice);
        String key = io.readLine("Enter " + strategy.getSearchType() + ": ");

        List<Book> result = strategy.search(ctx.bookService(), key);

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
}
