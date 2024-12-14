package prototype.projects;

/**
 * Enum des status dans le système
 *
 * <p>
 *     Les status pour les requêtes
 *     <ul>
 *         <li>{@link Status#EN_ATTENTE_APPROBATION}</li>
 *         <li>{@link Status#APPROUVE}</li>
 *     </ul>
 *     Les status pour les projets
 *     <ul>
 *         <li>{@link Status#PREVU}</li>
 *         <li>{@link Status#EN_COURS}</li>
 *         <li>{@link Status#TERMINE}</li>
 *     </ul>
 * </p>
 */
public enum Status {
    //Requests
    EN_ATTENTE_APPROBATION("En attente d'approbation"),
    APPROUVE("Approuvé"),

    //Projects
    PREVU("Prévu"),
    EN_COURS("En cours"),
    TERMINE("Terminé");

    private final String toString;

    private Status(String toString) {
        this.toString = toString;
    }

    /**
     * Méthode pour obtenir la version {@link String} du status
     * @return {@link String} du status
     */
    public String toString() {
        return toString;
    }

    public static Status getStatus(String toString) {
        for (Status status : Status.values()) {
            if (status.toString().equals(toString)) {
                return status;
            }
        }
        return null;
    }

}
