package library.management.system;

public final class BookSearchStrategyFactory {
    private BookSearchStrategyFactory() {}

    public static BookSearchStrategy fromChoice(int choice) {
        switch (choice) {
            case 1:
                return new SearchByNameStrategy();
            case 2:
                return new SearchByAuthorStrategy();
            case 3:
                return new SearchByPublisherStrategy();
            default:
                throw new IllegalArgumentException("Invalid search choice: " + choice);
        }
    }
}
