package prototype.users;

/**
 * Singleton pour obtenir l'utilisateur; Assure un seul utilisateur par session
 */
public class UserSession {
    private static UserSession instance;
    private String userId;
    private Utilisateur user; // This will store the current user (Resident or Intervenant)

    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Méthode pour la deconnexion.
     * <p>Set {@link #user} à null</p>
     */
    public void disconnect() {
        this.user = null;
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
    // Add this method to get the current resident if the user is a resident
    public Resident getResident() {
        if (user instanceof Resident) {
            return (Resident) user; // Cast to Resident
        } else {
            return null; // Or throw an exception if needed
        }
    }
}
