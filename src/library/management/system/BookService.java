package library.management.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> listBooks() {
        return bookRepo.findAll();
    }

    public Optional<Book> findBookByName(String name) {
        return bookRepo.findByName(name);
    }

    public void addBook(BookDetails details) {
        Book book = new Book(
                details.getName(),
                details.getAuthor(),
                details.getPublisher(),
                details.getQuantity(),
                details.getBorrowCopies(),
                details.getPrice()
        );
        bookRepo.save(book);
    }

    public boolean deleteBook(String name) {
        Optional<Book> book = bookRepo.findByName(name);
        if (book.isEmpty()) {
            return false;
        }
        bookRepo.deleteByName(name);
        return true;
    }

    public List<Book> searchByName(String key) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookRepo.findAll()) {
            if (book.getName().toLowerCase().contains(key.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByAuthor(String key) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookRepo.findAll()) {
            if (book.getAuthor().toLowerCase().contains(key.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByPublisher(String key) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookRepo.findAll()) {
            if (book.getPublisher().toLowerCase().contains(key.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public boolean decreaseBorrowCopies(String bookName) {
        List<Book> books = bookRepo.findAll();
        for (Book book : books) {
            if (book.getName().equalsIgnoreCase(bookName)) {
                if (book.getBrwcopies() <= 0) {
                    return false;
                }
                book.setBrwcopies(book.getBrwcopies() - 1);
                bookRepo.saveAll(books);
                return true;
            }
        }
        return false;
    }

    public boolean increaseBorrowCopies(String bookName) {
        List<Book> books = bookRepo.findAll();
        for (Book book : books) {
            if (book.getName().equalsIgnoreCase(bookName)) {
                book.setBrwcopies(book.getBrwcopies() + 1);
                bookRepo.saveAll(books);
                return true;
            }
        }
        return false;
    }
}
