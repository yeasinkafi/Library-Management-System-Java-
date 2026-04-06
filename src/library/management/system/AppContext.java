package library.management.system;

public class AppContext {
    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;
    private final BorrowingService borrowingService;
    private final DataService dataService;
    private final ConsoleIO io;

    public AppContext(UserService userService,
                      BookService bookService,
                      OrderService orderService,
                      BorrowingService borrowingService,
                      DataService dataService,
                      ConsoleIO io) {
        this.userService = userService;
        this.bookService = bookService;
        this.orderService = orderService;
        this.borrowingService = borrowingService;
        this.dataService = dataService;
        this.io = io;
    }

    public UserService userService() { return userService; }
    public BookService bookService() { return bookService; }
    public OrderService orderService() { return orderService; }
    public BorrowingService borrowingService() { return borrowingService; }
    public DataService dataService() { return dataService; }
    public ConsoleIO io() { return io; }
}
