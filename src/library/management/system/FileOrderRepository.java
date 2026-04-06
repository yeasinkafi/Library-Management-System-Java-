package library.management.system;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileOrderRepository implements OrderRepository {
    private final Path path;

    public FileOrderRepository() {
        this.path = DataFiles.dataPath("Orders");
        DataFiles.ensureDataDir();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        if (!Files.exists(path)) return orders;

        try {
            String content = Files.readString(path);
            String[] records = content.split("<NewOrder/>");
            for (String block : records) {
                Order o = parseOrderBlock(block);
                if (o != null) orders.add(o);
            }
        } catch (IOException e) {
            System.out.println("Error reading orders: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> findByPhoneNumber(String phoneNumber) {
        List<Order> out = new ArrayList<>();
        for (Order o : findAll()) {
            if (o.getPhonenumber() != null && o.getPhonenumber().equals(phoneNumber)) out.add(o);
        }
        return out;
    }

    @Override
    public void save(Order order) {
        List<Order> orders = findAll();
        orders.add(order);
        saveAll(orders);
    }

    @Override
    public void saveAll(List<Order> orders) {
        DataFiles.ensureDataDir();
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
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

    @Override
    public void deleteAll() {
        saveAll(new ArrayList<>());
    }

    private Order parseOrderBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return null;

        String bookName = null, userName = null, phone = null;
        int qty = 0;
        LocalDate date = null;
        Order.Status status = null;

        if (block.contains("BookName=") || block.contains("UserName=")) {
            String[] lines = block.split("\\R");
            for (String l : lines) {
                if (l.startsWith("BookName=")) bookName = l.substring("BookName=".length());
                else if (l.startsWith("Quantity=")) qty = ParseUtil.safeInt(l.substring("Quantity=".length()));
                else if (l.startsWith("UserName=")) userName = l.substring("UserName=".length());
                else if (l.startsWith("PhoneNumber=")) phone = l.substring("PhoneNumber=".length());
                else if (l.startsWith("Date=")) date = ParseUtil.safeDate(l.substring("Date=".length()));
                else if (l.startsWith("Status=")) status = ParseUtil.safeStatus(l.substring("Status=".length()));
            }
        }

        if (bookName == null && block.contains("<N/>")) {
            String[] parts = block.split("<N/>");
            if (parts.length >= 4) {
                bookName = parts[0].trim();
                userName = parts[1].trim();
                qty = ParseUtil.safeInt(parts[3]);
                phone = "";
            }
        }

        if (bookName == null || userName == null || phone == null) return null;
        if (date == null) date = LocalDate.now();
        if (status == null) status = Order.Status.CONFIRMED;

        return new Order(bookName, qty, userName, phone, date, status);
    }
}
