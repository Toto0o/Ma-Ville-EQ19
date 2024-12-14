package prototype.projects;

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
