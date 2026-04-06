package library.management.system;

public class BookDetails {
    private final String name;
    private final String author;
    private final String publisher;
    private final int quantity;
    private final int borrowCopies;
    private final double price;

    public BookDetails(String name, String author, String publisher, int quantity, int borrowCopies, double price) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.borrowCopies = borrowCopies;
        this.price = price;
    }

    public String getName() { return name; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getQuantity() { return quantity; }
    public int getBorrowCopies() { return borrowCopies; }
    public double getPrice() { return price; }
}
