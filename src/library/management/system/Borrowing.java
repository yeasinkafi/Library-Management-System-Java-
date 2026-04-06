package library.management.system;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Borrowing {
    private String bookName;
    private String userName;
    private String phoneNumber;
    private LocalDate start;
    private LocalDate finish;

    public Borrowing(String bookName, String userName, String phoneNumber, LocalDate start, LocalDate finish) {
        this.bookName = bookName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.start = start;
        this.finish = finish;
    }

    public String getBookName() { return bookName; }
    public String getUserName() { return userName; }
    public String getPhoneNumber() { return phoneNumber; }
    public LocalDate getStart() { return start; }
    public LocalDate getFinish() { return finish; }

    public void setBookName(String bookName) { this.bookName = bookName; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setStart(LocalDate start) { this.start = start; }
    public void setFinish(LocalDate finish) { this.finish = finish; }

    public long getDaysLeft() {
        return ChronoUnit.DAYS.between(LocalDate.now(), finish);
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(finish);
    }

    public long daysOverdue() {
        if (!isOverdue()) return 0;
        return ChronoUnit.DAYS.between(finish, LocalDate.now());
    }

    public long calculateFine(long finePerDay) {
        return daysOverdue() * finePerDay;
    }
}
