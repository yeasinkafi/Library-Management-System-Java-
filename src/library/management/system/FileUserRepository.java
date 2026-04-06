package library.management.system;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileUserRepository implements UserRepository {
    private final Path path;
    private final UserFactory factory;

    public FileUserRepository(UserFactory factory) {
        this.path = DataFiles.dataPath("Users");
        this.factory = factory;
        DataFiles.ensureDataDir();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        if (!Files.exists(path)) return users;

        try {
            String content = Files.readString(path);
            String[] records = content.split("<NewUser/>");
            for (String block : records) {
                User u = parseUserBlock(block);
                if (u != null) users.add(u);
            }
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public Optional<User> findByCredentials(String phoneNumber, String email, String password) {
        for (User u : findAll()) {
            if (u.getPhoneNumber().equals(phoneNumber)
                    && u.getEmail().equalsIgnoreCase(email)
                    && u.getPassword().equals(password)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        List<User> users = findAll();
        users.add(user);
        saveAll(users);
    }

    @Override
    public void saveAll(List<User> users) {
        DataFiles.ensureDataDir();
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (User s : users) {
                bw.write("Name=" + s.getName());
                bw.newLine();
                bw.write("PhoneNumber=" + s.getPhoneNumber());
                bw.newLine();
                bw.write("Email=" + s.getEmail());
                bw.newLine();
                bw.write("Password=" + s.getPassword());
                bw.newLine();

                UserType type = s.getUserType();
                bw.write("Type=" + (type == UserType.ADMIN ? "Admin" : "Normal User"));
                bw.newLine();
                bw.write("<NewUser/>");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private User parseUserBlock(String block) {
        block = block.trim();
        if (block.isEmpty()) return null;

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

        if (name == null || phone == null || email == null || password == null || type == null) return null;
        return factory.create(name, phone, email, password, UserType.fromText(type));
    }
}
