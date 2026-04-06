package library.management.system;

public class PlaceOrder implements IOOperation {

    @Override
    public String label() {
        return "Place Order";
    }

    @Override
    public void oper(AppContext ctx, User user) {
        ConsoleIO io = ctx.io();
        String bookName = io.readLine("Book name to order: ");
        int qty = io.readInt("Quantity: ");
        if (qty <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        OrderRequest request = new OrderRequest(bookName, qty, user.getName(), user.getPhoneNumber());
        ctx.orderService().placeOrder(request);
        System.out.println("Order placed. Status is pending.");
    }
}
