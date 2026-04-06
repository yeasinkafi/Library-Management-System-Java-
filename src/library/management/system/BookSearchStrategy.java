package library.management.system;

import java.util.List;

public interface BookSearchStrategy {
    List<Book> search(BookService bookService, String key);
    String getSearchType();
}
