package library.management.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class DataFiles {
    private DataFiles() {}

    public static Path dataPath(String fileName) {
        return Paths.get("data", fileName);
    }

    public static void ensureDataDir() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.out.println("Failed to create data directory: " + e.getMessage());
        }
    }
}
