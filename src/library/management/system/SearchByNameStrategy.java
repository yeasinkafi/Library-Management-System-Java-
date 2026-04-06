package library.management.system;

import java.util.List;

public class SearchByNameStrategy implements BookSearchStrategy {

    @Override
    public List<Book> search(BookService bookService, String key) {
        return bookService.searchByName(key);
    }

    @Override
    public String getSearchType() {
        return "Book name";
    }
}
