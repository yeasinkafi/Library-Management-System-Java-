package library.management.system;

import java.util.List;

public class SearchByAuthorStrategy implements BookSearchStrategy {

    @Override
    public List<Book> search(BookService bookService, String key) {
        return bookService.searchByAuthor(key);
    }

    @Override
    public String getSearchType() {
        return "Author";
    }
}
