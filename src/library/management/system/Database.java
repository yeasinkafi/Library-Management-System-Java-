package library.management.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {

    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<Order> orders = new ArrayList<>();
    private final ArrayList<Borrowing> borrowings = new ArrayList<>();

    private Path dataPath(String fileName) {
        return Paths.get("data", fileName);
    }

    private void ensureDataDir() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.out.println("Failed to create data directory: " + e.getMessage());
        }
    }

    // ---------- USERS ----------
    public void addUser(User user) {
        users.add(user);
    }

    public User login(String phonenumber, String email, String password) {
        for (User s : users) {
            if (s.getPhoneNumber().equals(phonenumber)
                    && s.getEmail().equalsIgnoreCase(email)
                    && s.getPassword().equals(password)) {
                return s;
            }
        }
        return null;
    }

    public void readUsers() {
        ensureDataDir();
        Path p = dataPath("Users");
        if (!Files.exists(p)) return;

        
        try {
            String content = Files.readString(p);
            String[] records = content.split("<NewUser/>");
            for (String r : records) {
                parseUserBlock(r);
            }
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
    }

    private void parseUserBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return;

        String name = null, phone = null, email = null, password = null, type = null;

        
        if (block.contains("Name=") || block.contains("PhoneNumber=")) {
            String[] lines = block.split("\\R");
            for (String l : lines) {
                if (l.startsWith("Name=")) name = l.substring("Name=".length());
                else if (l.startsWith("PhoneNumber=")) phone = l.substring("PhoneNumber=".length());
                else if (l.startsWith("Email=")) email = l.substring("Email=".length());
                else if (l.startsWith("Password=")) password = l.substring("Password=".length());
                else if (l.startsWith("Type=")) type = l.substring("Type=".length());
            }
        }

        
        if (name == null && block.contains("<N/>")) {
            String[] parts = block.split("<N/>");
            if (parts.length >= 4) {
                name = parts[0].trim();
                email = parts[1].trim();
                phone = parts[2].trim();
                type = parts[3].trim();
                password = "1234";
            }
        }

        if (name == null || phone == null || email == null || password == null || type == null) return;

        User u;
        if (type.equalsIgnoreCase("Admin")) {
            u = new Admin(name, phone, email, password);
        } else {
            u = new NormalUser(name, phone, email, password);
        }
        users.add(u);
    }

    public void saveUsers() {
        ensureDataDir();
        Path p = dataPath("Users");
        try (BufferedWriter bw = Files.newBufferedWriter(p)) {
            for (User s : users) {
                bw.write("Name=" + s.getName());
                bw.newLine();
                bw.write("PhoneNumber=" + s.getPhoneNumber());
                bw.newLine();
                bw.write("Email=" + s.getEmail());
                bw.newLine();
                bw.write("Password=" + s.getPassword());
                bw.newLine();
                bw.write("Type=" + (s instanceof Admin ? "Admin" : "Normal User"));
                bw.newLine();
                bw.write("<NewUser/>");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // ---------- BOOKS ----------
    public ArrayList<Book> getBooks() {
        return books;
    }

    public Book findBookByName(String bookName) {
        for (Book b : books) {
            if (b.getName().equalsIgnoreCase(bookName)) return b;
        }
        return null;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(String bookname) {
        books.removeIf(b -> b.getName().equalsIgnoreCase(bookname));
    }

    public void readBooks() {
        ensureDataDir();
        Path p = dataPath("Books");
        if (!Files.exists(p)) return;

        
        try {
            String content = Files.readString(p);
            String[] records = content.split("<NewBook/>");
            for (String r : records) {
                parseBookBlock(r);
            }
        } catch (IOException e) {
            System.out.println("Error reading books: " + e.getMessage());
        }
    }

    private void parseBookBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return;

        String name = null, author = null, publisher = null;
        int qty = 0, brwCopies = 0;
        double price = 0;

        
        if (block.contains("Name=") || block.contains("Author=")) {
            String[] lines = block.split("\\R");
            for (String l : lines) {
                if (l.startsWith("Name=")) name = l.substring("Name=".length());
                else if (l.startsWith("Author=")) author = l.substring("Author=".length());
                else if (l.startsWith("Publisher=")) publisher = l.substring("Publisher=".length());
                else if (l.startsWith("Quantity=")) qty = safeInt(l.substring("Quantity=".length()));
                else if (l.startsWith("BorrowCopies=")) brwCopies = safeInt(l.substring("BorrowCopies=".length()));
                else if (l.startsWith("Price=")) price = safeDouble(l.substring("Price=".length()));
            }
        }

        
        if (name == null && block.contains("<N/>")) {
            String[] parts = block.split("<N/>");
            if (parts.length >= 7) {
                name = parts[0].trim();
                author = parts[1].trim();
                publisher = parts[2].trim();
                qty = safeInt(parts[4]);
                price = safeDouble(parts[5]);
                brwCopies = safeInt(parts[6]);
            }
        }

        if (name == null || author == null || publisher == null) return;
        books.add(new Book(name, author, publisher, qty, brwCopies, price));
    }

    public void saveBooks() {
        ensureDataDir();
        Path p = dataPath("Books");
        try (BufferedWriter bw = Files.newBufferedWriter(p)) {
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

    // ---------- ORDERS ----------
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void readOrders() {
        ensureDataDir();
        Path p = dataPath("Orders");
        if (!Files.exists(p)) return;

        
        try {
            String content = Files.readString(p);
            String[] records = content.split("<NewOrder/>");
            for (String r : records) {
                parseOrderBlock(r);
            }
        } catch (IOException e) {
            System.out.println("Error reading orders: " + e.getMessage());
        }
    }

    private void parseOrderBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return;

        String bookName = null, userName = null, phone = null;
        int qty = 0;
        LocalDate date = null;
        Order.Status status = null;

        
        if (block.contains("BookName=") || block.contains("UserName=")) {
            String[] lines = block.split("\\R");
            for (String l : lines) {
                if (l.startsWith("BookName=")) bookName = l.substring("BookName=".length());
                else if (l.startsWith("Quantity=")) qty = safeInt(l.substring("Quantity=".length()));
                else if (l.startsWith("UserName=")) userName = l.substring("UserName=".length());
                else if (l.startsWith("PhoneNumber=")) phone = l.substring("PhoneNumber=".length());
                else if (l.startsWith("Date=")) date = safeDate(l.substring("Date=".length()));
                else if (l.startsWith("Status=")) status = safeStatus(l.substring("Status=".length()));
            }
        }


        if (bookName == null && block.contains("<N/>")) {
            String[] parts = block.split("<N/>");
            if (parts.length >= 4) {
                bookName = parts[0].trim();
                userName = parts[1].trim();
                qty = safeInt(parts[3]);
                phone = "";
            }
        }

        if (bookName == null || userName == null || phone == null) return;


        if (date == null) date = LocalDate.now();
        if (status == null) status = Order.Status.CONFIRMED;

        orders.add(new Order(bookName, qty, userName, phone, date, status));
    }

    public void saveOrders() {
        ensureDataDir();
        Path p = dataPath("Orders");
        try (BufferedWriter bw = Files.newBufferedWriter(p)) {
            for (Order o : orders) {
                bw.write("BookName=" + o.getBookname());
                bw.newLine();
                bw.write("Quantity=" + o.getQty());
                bw.newLine();
                bw.write("UserName=" + o.getUsername());
                bw.newLine();
                bw.write("PhoneNumber=" + o.getPhonenumber());
                bw.newLine();
                bw.write("Date=" + o.getCreatedDate());
                bw.newLine();
                bw.write("Status=" + o.getStatus());
                bw.newLine();
                bw.write("<NewOrder/>");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }


    public boolean confirmOrder(Order order) {
        if (order.getStatus() != Order.Status.PENDING) return false;

        Book book = findBookByName(order.getBookname());
        if (book == null) return false;

        if (book.getQty() < order.getQty()) {
            return false; 
        }

        int newQty = book.getQty() - order.getQty();
        book.setQty(newQty);

        
        int reduceBorrowable = Math.min(book.getBrwcopies(), order.getQty());
        book.setBrwcopies(book.getBrwcopies() - reduceBorrowable);

        order.setStatus(Order.Status.CONFIRMED);
        saveBooks();
        saveOrders();
        return true;
    }

    public boolean rejectOrder(Order order) {
        if (order.getStatus() != Order.Status.PENDING) return false;
        order.setStatus(Order.Status.REJECTED);
        saveOrders();
        return true;
    }

    // ---------- BORROWINGS ----------
    public ArrayList<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void addBorrowing(Borrowing b) {
        borrowings.add(b);
    }

    public void readBorrowings() {
        ensureDataDir();
        Path p = dataPath("Borrowings");
        if (!Files.exists(p)) return;

        
        try {
            String content = Files.readString(p);
            String[] records = content.split("<NewBorrowing/>");
            for (String r : records) {
                parseBorrowingBlock(r);
            }
        } catch (IOException e) {
            System.out.println("Error reading borrowings: " + e.getMessage());
        }
    }

    private void parseBorrowingBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return;

        String bookName = null, userName = null, phone = null;
        LocalDate start = null, finish = null;


        if (block.contains("BookName=") || block.contains("Start=")) {
            String[] lines = block.split("\\R");
            for (String l : lines) {
                if (l.startsWith("BookName=")) bookName = l.substring("BookName=".length());
                else if (l.startsWith("UserName=")) userName = l.substring("UserName=".length());
                else if (l.startsWith("PhoneNumber=")) phone = l.substring("PhoneNumber=".length());
                else if (l.startsWith("Start=")) start = safeDate(l.substring("Start=".length()));
                else if (l.startsWith("Finish=")) finish = safeDate(l.substring("Finish=".length()));
            }
        }


        if (bookName == null && block.contains("<N/>")) {
            String[] parts = block.split("<N/>");
            
            if (parts.length >= 5) {
                String p0 = parts[0].trim();
                String p1 = parts[1].trim();
                String p2 = parts[2].trim();
                LocalDate d3 = safeDate(parts[3]);
                LocalDate d4 = safeDate(parts[4]);
                if (!p0.isEmpty() && !p1.isEmpty() && d3 != null && d4 != null) {
                    bookName = p0;
                    userName = p1;
                    phone = p2;
                    start = d3;
                    finish = d4;
                }
            }

            
            if (bookName == null && parts.length >= 5) {
                LocalDate d0 = safeDate(parts[0]);
                LocalDate d1 = safeDate(parts[1]);
                String p3 = parts[3].trim();
                String p4 = parts[4].trim();
                if (d0 != null && d1 != null && !p3.isEmpty() && !p4.isEmpty()) {
                    start = d0;
                    finish = d1;
                    userName = p3;
                    bookName = p4;
                    phone = (parts.length >= 6) ? parts[5].trim() : "";
                }
            }
        }

        if (bookName == null || userName == null || phone == null || start == null || finish == null) return;
        borrowings.add(new Borrowing(bookName, userName, phone, start, finish));
    }

    public void saveBorrowings() {
        ensureDataDir();
        Path p = dataPath("Borrowings");
        try (BufferedWriter bw = Files.newBufferedWriter(p)) {
            for (Borrowing b : borrowings) {
                bw.write("BookName=" + b.getBookName());
                bw.newLine();
                bw.write("UserName=" + b.getUserName());
                bw.newLine();
                bw.write("PhoneNumber=" + b.getPhoneNumber());
                bw.newLine();
                bw.write("Start=" + b.getStart());
                bw.newLine();
                bw.write("Finish=" + b.getFinish());
                bw.newLine();
                bw.write("<NewBorrowing/>");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving borrowings: " + e.getMessage());
        }
    }

    // ---------- UTIL ----------
    public void deleteAllData() {
        users.clear();
        books.clear();
        orders.clear();
        borrowings.clear();

        ensureDataDir();
        try {
            Files.deleteIfExists(dataPath("Users"));
            Files.deleteIfExists(dataPath("Books"));
            Files.deleteIfExists(dataPath("Orders"));
            Files.deleteIfExists(dataPath("Borrowings"));
        } catch (IOException e) {
            System.out.println("Error deleting data: " + e.getMessage());
        }
    }

    private int safeInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }

    private double safeDouble(String s) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return 0.0; }
    }

    private LocalDate safeDate(String s) {
        try { return LocalDate.parse(s.trim()); } catch (Exception e) { return null; }
    }

    private Order.Status safeStatus(String s) {
        try { return Order.Status.valueOf(s.trim().toUpperCase()); } catch (Exception e) { return null; }
    }
}
