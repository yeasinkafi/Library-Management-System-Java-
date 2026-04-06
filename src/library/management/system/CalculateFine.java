package library.management.system;

import java.util.List;

public class CalculateFine implements IOOperation {
    private static final long FINE_PER_DAY = 10;

    @Override
    public String label() {
        return "Calculate Fine";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        System.out.println("\n=== Calculate Fine ===");
        List<Borrowing> borrowings = ctx.borrowingService().listBorrowings();
        if (borrowings.isEmpty()) {
            System.out.println("No borrowings found.");
            return;
        }

        boolean any = false;
        for (Borrowing borrowing : borrowings) {
            if (borrowing.isOverdue()) {
                any = true;
                printFineDetails(borrowing);
            }
        }

        if (!any) {
            System.out.println("No overdue borrowings.");
        }
    }

    private void printFineDetails(Borrowing borrowing) {
        System.out.println("-----------------------------------");
        System.out.println("User: " + borrowing.getUserName());
        System.out.println("Phone: " + borrowing.getPhoneNumber());
        System.out.println("Book: " + borrowing.getBookName());
        System.out.println("Overdue days: " + borrowing.daysOverdue());
        System.out.println("Fine: " + borrowing.calculateFine(FINE_PER_DAY));
    }
}
