package library.management.system;

import java.util.List;

public interface OrderRepository {
    List<Order> findAll();
    List<Order> findByPhoneNumber(String phoneNumber);
    void save(Order order);
    void saveAll(List<Order> orders);
    void deleteAll();
}
