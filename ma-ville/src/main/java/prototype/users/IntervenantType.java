package prototype.users;

public enum IntervenantType {
    ENTREPRISE_PUBLIQUE("entreprise publique"),
    ENTREPRENUR_PRIVÉ("entrepreneur privé"),
    PARTICULIER("particulier");

    private final String toString;

    private IntervenantType(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}
