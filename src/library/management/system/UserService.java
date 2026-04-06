package library.management.system;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> login(LoginRequest request) {
        return userRepo.findByCredentials(request.getPhoneNumber(), request.getEmail(), request.getPassword());
    }

    public void registerUser(UserRegistration registration) {
        User user = createUser(registration);
        userRepo.save(user);
    }

    private User createUser(UserRegistration registration) {
        return registration.getUserType().createUser(
                registration.getName(),
                registration.getPhoneNumber(),
                registration.getEmail(),
                registration.getPassword()
        );
    }
}
