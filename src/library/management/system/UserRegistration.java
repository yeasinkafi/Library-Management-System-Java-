package library.management.system;

public class UserRegistration {
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final String password;
    private final UserType userType;

    public UserRegistration(String name, String phoneNumber, String email, String password, UserType userType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public UserType getUserType() { return userType; }
}
