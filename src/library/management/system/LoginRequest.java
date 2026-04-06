package library.management.system;

public class LoginRequest {
    private final String phoneNumber;
    private final String email;
    private final String password;

    public LoginRequest(String phoneNumber, String email, String password) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
