package library.management.system;

public class Book {
    private String name;
    private String author;
    private String publisher;
    private int qty;
    private int brwcopies;
    private double price;

    public Book(String name, String author, String publisher, int qty, int brwcopies, double price) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.qty = qty;
        this.brwcopies = brwcopies;
        this.price = price;
    }

    public String getName() { return name; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getQty() { return qty; }
    public int getBrwcopies() { return brwcopies; }
    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setQty(int qty) { this.qty = qty; }
    public void setBrwcopies(int brwcopies) { this.brwcopies = brwcopies; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", qty=" + qty +
                ", brwcopies=" + brwcopies +
                ", price=" + price +
                '}';
    }
}
