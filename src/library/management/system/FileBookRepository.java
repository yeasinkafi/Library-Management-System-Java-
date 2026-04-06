package library.management.system;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileBookRepository implements BookRepository {
    private final Path path;

    public FileBookRepository() {
        this.path = DataFiles.dataPath("Books");
        DataFiles.ensureDataDir();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        if (!Files.exists(path)) return books;

        try {
            String content = Files.readString(path);
            String[] records = content.split("<NewBook/>");
            for (String block : records) {
                Book b = parseBookBlock(block);
                if (b != null) books.add(b);
            }
        } catch (IOException e) {
            System.out.println("Error reading books: " + e.getMessage());
        }
        return books;
    }

    @Override
    public Optional<Book> findByName(String name) {
        for (Book b : findAll()) {
            if (b.getName().equalsIgnoreCase(name)) return Optional.of(b);
        }
        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        List<Book> books = findAll();
        books.add(book);
        saveAll(books);
    }

    @Override
    public void deleteByName(String name) {
        List<Book> books = findAll();
        books.removeIf(b -> b.getName().equalsIgnoreCase(name));
        saveAll(books);
    }

    @Override
    public void saveAll(List<Book> books) {
        DataFiles.ensureDataDir();
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Book b : books) {
                bw.write("Name=" + b.getName());
                bw.newLine();
                bw.write("Author=" + b.getAuthor());
                bw.newLine();
                bw.write("Publisher=" + b.getPublisher());
                bw.newLine();
                bw.write("Quantity=" + b.getQty());
                bw.newLine();
                bw.write("BorrowCopies=" + b.getBrwcopies());
                bw.newLine();
                bw.write("Price=" + b.getPrice());
                bw.newLine();
                bw.write("<NewBook/>");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private Book parseBookBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return null;

        String name = null, author = null, publisher = null;
        int qty = 0, brwCopies = 0;
        double price = 0;

        if (block.contains("Name=") || block.contains("Author=")) {
            String[] lines = block.split("\\R");
            for (String l : lines) {
                if (l.startsWith("Name=")) name = l.substring("Name=".length());
                else if (l.startsWith("Author=")) author = l.substring("Author=".length());
                else if (l.startsWith("Publisher=")) publisher = l.substring("Publisher=".length());
                else if (l.startsWith("Quantity=")) qty = ParseUtil.safeInt(l.substring("Quantity=".length()));
                else if (l.startsWith("BorrowCopies=")) brwCopies = ParseUtil.safeInt(l.substring("BorrowCopies=".length()));
                else if (l.startsWith("Price=")) price = ParseUtil.safeDouble(l.substring("Price=".length()));
            }
        }

        // legacy support
        if (name == null && block.contains("<N/>")) {
            String[] parts = block.split("<N/>");
            if (parts.length >= 7) {
                name = parts[0].trim();
                author = parts[1].trim();
                publisher = parts[2].trim();
                qty = ParseUtil.safeInt(parts[4]);
                price = ParseUtil.safeDouble(parts[5]);
                brwCopies = ParseUtil.safeInt(parts[6]);
            }
        }

        if (name == null || author == null || publisher == null) return null;
        return new Book(name, author, publisher, qty, brwCopies, price);
    }
}
