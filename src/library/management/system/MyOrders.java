package library.management.system;

import java.util.List;

public class MyOrders implements IOOperation {

    @Override
    public String label() {
        return "My Orders";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        List<Order> orders = ctx.orderService().listOrdersByUser(user.getPhoneNumber());
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        System.out.println("\n--- My Orders ---");
        for (Order o : orders) {
            System.out.println("Book: " + o.getBookname() + " | Qty: " + o.getQty() + " | Status: " + o.getStatus());
        }
    }
}
