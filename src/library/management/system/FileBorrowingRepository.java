package library.management.system;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileBorrowingRepository implements BorrowingRepository {
    private final Path path;

    public FileBorrowingRepository() {
        this.path = DataFiles.dataPath("Borrowings");
        DataFiles.ensureDataDir();
    }

    @Override
    public List<Borrowing> findAll() {
        List<Borrowing> borrowings = new ArrayList<>();
        if (!Files.exists(path)) return borrowings;

        try {
            String content = Files.readString(path);
            String[] records = content.split("<NewBorrowing/>");
            for (String block : records) {
                Borrowing b = parseBorrowingBlock(block);
                if (b != null) borrowings.add(b);
            }
        } catch (IOException e) {
            System.out.println("Error reading borrowings: " + e.getMessage());
        }
        return borrowings;
    }

    @Override
    public List<Borrowing> findByPhoneNumber(String phoneNumber) {
        List<Borrowing> out = new ArrayList<>();
        for (Borrowing b : findAll()) {
            if (b.getPhoneNumber() != null && b.getPhoneNumber().equals(phoneNumber)) out.add(b);
        }
        return out;
    }

    @Override
    public void save(Borrowing borrowing) {
        List<Borrowing> list = findAll();
        list.add(borrowing);
        saveAll(list);
    }

    @Override
    public void remove(Borrowing borrowing) {
        List<Borrowing> list = findAll();
        list.removeIf(b -> sameBorrowing(b, borrowing));
        saveAll(list);
    }

    @Override
    public void saveAll(List<Borrowing> borrowings) {
        DataFiles.ensureDataDir();
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
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

    @Override
    public void deleteAll() {
        saveAll(new ArrayList<>());
    }

    private boolean sameBorrowing(Borrowing a, Borrowing b) {
        return a.getBookName().equalsIgnoreCase(b.getBookName())
                && a.getUserName().equalsIgnoreCase(b.getUserName())
                && a.getPhoneNumber().equals(b.getPhoneNumber())
                && a.getStart().equals(b.getStart())
                && a.getFinish().equals(b.getFinish());
    }

    private Borrowing parseBorrowingBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return null;

        String bookName = null, userName = null, phone = null;
        LocalDate start = null, finish = null;

        if (block.contains("BookName=") || block.contains("Start=")) {
            String[] lines = block.split("\\R");
            for (String l : lines) {
                if (l.startsWith("BookName=")) bookName = l.substring("BookName=".length());
                else if (l.startsWith("UserName=")) userName = l.substring("UserName=".length());
                else if (l.startsWith("PhoneNumber=")) phone = l.substring("PhoneNumber=".length());
                else if (l.startsWith("Start=")) start = ParseUtil.safeDate(l.substring("Start=".length()));
                else if (l.startsWith("Finish=")) finish = ParseUtil.safeDate(l.substring("Finish=".length()));
            }
        }

        if (bookName == null && block.contains("<N/>")) {
            String[] parts = block.split("<N/>");

            if (parts.length >= 5) {
                String p0 = parts[0].trim();
                String p1 = parts[1].trim();
                String p2 = parts[2].trim();
                LocalDate d3 = ParseUtil.safeDate(parts[3]);
                LocalDate d4 = ParseUtil.safeDate(parts[4]);
                if (!p0.isEmpty() && !p1.isEmpty() && d3 != null && d4 != null) {
                    bookName = p0;
                    userName = p1;
                    phone = p2;
                    start = d3;
                    finish = d4;
                }
            }

            if (bookName == null && parts.length >= 5) {
                LocalDate d0 = ParseUtil.safeDate(parts[0]);
                LocalDate d1 = ParseUtil.safeDate(parts[1]);
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

        if (bookName == null || userName == null || phone == null || start == null || finish == null) return null;
        return new Borrowing(bookName, userName, phone, start, finish);
    }
}
