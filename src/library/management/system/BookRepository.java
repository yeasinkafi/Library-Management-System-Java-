package library.management.system;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findByName(String name);
    void save(Book book);
    void deleteByName(String name);
    void saveAll(List<Book> books);
}
