package prototype.Users;

public class UserSession {
    private static UserSession instance;
    private String userId;
    private Utilisateur user; // This will store the current user (Resident or Intervenant)

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Setters and getters for userId and user
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    // Setters and getters for user
    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Utilisateur getUser() {
        return user;
    }
}
