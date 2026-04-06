package library.management.system;

import java.time.LocalDate;
import java.util.List;

public class BorrowingService {
    private final BorrowingRepository borrowingRepo;
    private final BookService bookService;

    public BorrowingService(BorrowingRepository borrowingRepo, BookService bookService) {
        this.borrowingRepo = borrowingRepo;
        this.bookService = bookService;
    }

    public List<Borrowing> listBorrowings() {
        return borrowingRepo.findAll();
    }

    public List<Borrowing> listBorrowingsByUser(String phoneNumber) {
        return borrowingRepo.findByPhoneNumber(phoneNumber);
    }

    public boolean borrowBook(String bookName, User user) {
        if (bookService.findBookByName(bookName).isEmpty()) {
            return false;
        }
        if (!bookService.decreaseBorrowCopies(bookName)) {
            return false;
        }

        LocalDate start = LocalDate.now();
        LocalDate finish = start.plusDays(14);
        Borrowing borrowing = new Borrowing(bookName, user.getName(), user.getPhoneNumber(), start, finish);
        borrowingRepo.save(borrowing);
        return true;
    }

    public boolean returnBook(String bookName, User user) {
        List<Borrowing> borrowings = borrowingRepo.findAll();
        Borrowing match = findMatchingBorrowing(bookName, user, borrowings);
        if (match == null) {
            return false;
        }

        borrowingRepo.remove(match);
        bookService.increaseBorrowCopies(bookName);
        return true;
    }

    private Borrowing findMatchingBorrowing(String bookName, User user, List<Borrowing> borrowings) {
        for (Borrowing borrowing : borrowings) {
            boolean sameBook = borrowing.getBookName().equalsIgnoreCase(bookName);
            boolean sameUser = borrowing.getPhoneNumber().equals(user.getPhoneNumber());
            if (sameBook && sameUser) {
                return borrowing;
            }
        }
        return null;
    }

    public void deleteAll() {
        borrowingRepo.deleteAll();
    }
}
