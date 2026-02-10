package library.management.system;

import java.util.Scanner;

public class DeleteAllData implements IOOperation {

    @Override
    public void oper(Database database, User user, Scanner scanner) {
        System.out.println("\n=== Delete All Data ===");
        System.out.print("Are you sure? Type YES to confirm: ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("YES")) {
            database.deleteAllData();
            System.out.println("All data deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }
}
