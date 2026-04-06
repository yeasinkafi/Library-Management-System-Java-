package library.management.system;

public class OrderRequest {
    private final String bookName;
    private final int quantity;
    private final String userName;
    private final String phoneNumber;

    public OrderRequest(String bookName, int quantity, String userName, String phoneNumber) {
        this.bookName = bookName;
        this.quantity = quantity;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getBookName() { return bookName; }
    public int getQuantity() { return quantity; }
    public String getUserName() { return userName; }
    public String getPhoneNumber() { return phoneNumber; }
}
