package library.management.system;

import java.util.List;

public class ViewOrders implements IOOperation {
    @Override
    public String label() {
        return "View Orders";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        List<Order> orders = ctx.orderService().listOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n--- Orders ---");
        for (Order o : orders) {
            System.out.println("Book: " + o.getBookname());
            System.out.println("Qty: " + o.getQty());
            System.out.println("User: " + o.getUsername());
            System.out.println("Phone: " + o.getPhonenumber());
            System.out.println("Date: " + o.getCreatedDate());
            System.out.println("Status: " + o.getStatus());
            System.out.println();
        }
    }
}
