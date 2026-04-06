package library.management.system;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepo;

    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> listOrders() {
        return orderRepo.findAll();
    }

    public List<Order> listOrdersByUser(String phoneNumber) {
        return orderRepo.findByPhoneNumber(phoneNumber);
    }

    public void placeOrder(OrderRequest request) {
        Order order = new Order(
                request.getBookName(),
                request.getQuantity(),
                request.getUserName(),
                request.getPhoneNumber()
        );
        orderRepo.save(order);
    }

    public void deleteAll() {
        orderRepo.deleteAll();
    }
}
