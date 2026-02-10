package library.management.system;

import java.time.LocalDate;

public class Order {

    public enum Status {
        PENDING,
        CONFIRMED,
        REJECTED,
        CANCELLED
    }

    private String bookname;
    private int qty;
    private String username;
    private String phonenumber;
    private LocalDate createdDate;
    private Status status;

    public Order(String bookname, int qty, String username, String phonenumber) {
        this(bookname, qty, username, phonenumber, LocalDate.now(), Status.PENDING);
    }

    public Order(String bookname, int qty, String username, String phonenumber, LocalDate createdDate, Status status) {
        this.bookname = bookname;
        this.qty = qty;
        this.username = username;
        this.phonenumber = phonenumber;
        this.createdDate = createdDate;
        this.status = status;
    }

    public String getBookname() { return bookname; }
    public int getQty() { return qty; }
    public String getUsername() { return username; }
    public String getPhonenumber() { return phonenumber; }
    public LocalDate getCreatedDate() { return createdDate; }
    public Status getStatus() { return status; }

    public void setBookname(String bookname) { this.bookname = bookname; }
    public void setQty(int qty) { this.qty = qty; }
    public void setUsername(String username) { this.username = username; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public void setStatus(Status status) { this.status = status; }
}
