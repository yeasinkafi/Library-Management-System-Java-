package library.management.system;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findByCredentials(String phoneNumber, String email, String password);
    void save(User user);
    void saveAll(List<User> users);
}
