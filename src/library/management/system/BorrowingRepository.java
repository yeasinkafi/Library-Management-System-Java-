package library.management.system;

import java.util.List;

public interface BorrowingRepository {
    List<Borrowing> findAll();
    List<Borrowing> findByPhoneNumber(String phoneNumber);
    void save(Borrowing borrowing);
    void remove(Borrowing borrowing);
    void saveAll(List<Borrowing> borrowings);
    void deleteAll();
}
