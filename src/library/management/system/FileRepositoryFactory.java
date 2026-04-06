package library.management.system;

public class FileRepositoryFactory implements RepositoryFactory {
    private final UserFactory userFactory;

    public FileRepositoryFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public UserRepository createUserRepository() {
        return new FileUserRepository(userFactory);
    }

    @Override
    public BookRepository createBookRepository() {
        return new FileBookRepository();
    }

    @Override
    public OrderRepository createOrderRepository() {
        return new FileOrderRepository();
    }

    @Override
    public BorrowingRepository createBorrowingRepository() {
        return new FileBorrowingRepository();
    }
}
