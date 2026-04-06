package library.management.system;

public enum UserType {
    ADMIN {
        @Override
        public User createUser(String name, String phoneNumber, String email, String password) {
            return new Admin(name, phoneNumber, email, password);
        }
    },
    NORMAL_USER {
        @Override
        public User createUser(String name, String phoneNumber, String email, String password) {
            return new NormalUser(name, phoneNumber, email, password);
        }
    };

    public abstract User createUser(String name, String phoneNumber, String email, String password);

    public static UserType fromText(String text) {
        if (text == null) return NORMAL_USER;
        String t = text.trim().toLowerCase();
        if (t.equals("admin")) return ADMIN;
        return NORMAL_USER;
    }

    public static UserType fromUserInput(String text) {
        if (text != null && text.trim().equalsIgnoreCase("Admin")) {
            return ADMIN;
        }
        return NORMAL_USER;
    }
}
