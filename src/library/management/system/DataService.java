package library.management.system;

import java.util.ArrayList;

public class DataService {
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    private final OrderRepository orderRepo;
    private final BorrowingRepository borrowingRepo;

    public DataService(UserRepository userRepo,
                       BookRepository bookRepo,
                       OrderRepository orderRepo,
                       BorrowingRepository borrowingRepo) {
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.orderRepo = orderRepo;
        this.borrowingRepo = borrowingRepo;
    }

    public void deleteAllData() {
        bookRepo.saveAll(new ArrayList<>());
        userRepo.saveAll(new ArrayList<>());
        orderRepo.deleteAll();
        borrowingRepo.deleteAll();
    }
}
