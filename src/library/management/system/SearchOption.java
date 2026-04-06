package library.management.system;

public enum SearchOption {
    NAME,
    AUTHOR,
    PUBLISHER;

    public static SearchOption fromChoice(int choice) {
        if (choice == 1) {
            return NAME;
        }
        if (choice == 2) {
            return AUTHOR;
        }
        return PUBLISHER;
    }
}
