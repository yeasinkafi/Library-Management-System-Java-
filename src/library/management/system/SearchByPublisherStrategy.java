package library.management.system;

import java.util.List;

public class SearchByPublisherStrategy implements BookSearchStrategy {

    @Override
    public List<Book> search(BookService bookService, String key) {
        return bookService.searchByPublisher(key);
    }

    @Override
    public String getSearchType() {
        return "Publisher";
    }
}
