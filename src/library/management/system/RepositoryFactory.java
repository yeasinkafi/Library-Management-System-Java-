package library.management.system;

public interface RepositoryFactory {
    UserRepository createUserRepository();
    BookRepository createBookRepository();
    OrderRepository createOrderRepository();
    BorrowingRepository createBorrowingRepository();
}
