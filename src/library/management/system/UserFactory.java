package library.management.system;

public class UserFactory {
    public User create(String name, String phone, String email, String password, UserType type) {
        return type.createUser(name, phone, email, password);
    }
}
