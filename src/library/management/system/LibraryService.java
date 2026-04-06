package library.management.system;

import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;
    private final BorrowingService borrowingService;
    private final DataService dataService;

    public LibraryService(UserService userService,
                          BookService bookService,
                          OrderService orderService,
                          BorrowingService borrowingService,
                          DataService dataService) {
        this.userService = userService;
        this.bookService = bookService;
        this.orderService = orderService;
        this.borrowingService = borrowingService;
        this.dataService = dataService;
    }

    public Optional<User> login(LoginRequest request) {
        return userService.login(request);
    }

    public void registerUser(UserRegistration registration) {
        userService.registerUser(registration);
    }

    public java.util.List<Book> listBooks() {
        return bookService.listBooks();
    }

    public Optional<Book> findBookByName(String name) {
        return bookService.findBookByName(name);
    }

    public void addBook(BookDetails details) {
        bookService.addBook(details);
    }

    public boolean deleteBook(String name) {
        return bookService.deleteBook(name);
    }

    public java.util.List<Order> listOrders() {
        return orderService.listOrders();
    }

    public java.util.List<Order> listOrdersByUser(String phoneNumber) {
        return orderService.listOrdersByUser(phoneNumber);
    }

    public void placeOrder(OrderRequest request) {
        orderService.placeOrder(request);
    }

    public java.util.List<Borrowing> listBorrowings() {
        return borrowingService.listBorrowings();
    }

    public java.util.List<Borrowing> listBorrowingsByUser(String phoneNumber) {
        return borrowingService.listBorrowingsByUser(phoneNumber);
    }

    public boolean borrowBook(String bookName, User user) {
        return borrowingService.borrowBook(bookName, user);
    }

    public boolean returnBook(String bookName, User user) {
        return borrowingService.returnBook(bookName, user);
    }

    public void deleteAllData() {
        dataService.deleteAllData();
    }
}
