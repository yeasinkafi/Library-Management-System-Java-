package library.management.system;

import java.util.Scanner;

public class Exit implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("Exiting program...");
        System.exit(0);
    }
}
