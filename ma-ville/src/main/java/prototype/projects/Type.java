package prototype.projects;

/**
 * Type des travaux et projets
 */
public enum Type {

    TRAVAUX_ROUTIERS("travaux routiers"),
    TRAVAUX_GAZ_ELECTRICITE("travaux de gaz et d'électricité"),
    CONSTRUCTION_RENOVATION("contruction et rénovation"),
    ENTRETIEN_PAYSAGER("entretien paysager"),
    TRAVAUX_TRANSPORTS_COMMUN("travaux de transport en commun"),
    TRAVAUX_SIGNALISATION_ECLAIRAGE("travaux de singalisation et éclairage"),
    TRAVAUX_SOUTERRAINS("travaux souterrains"),
    TRAVAIL_RESIDENTIEL("travail résidentiel"),
    ENTRETIEN_URBAIN("entretien urbain"),
    ENTRETIEN_RESEAUX_TELECOMMUNICATION("entretien des réseaux de télécommunication");

    private final String toString;

    private Type(String toString) {
        this.toString = toString;
    }

    /**
     * Méthode pour obtenir le type en {@link String}
     * @return {@link String} du type
     */
    public String toString() {
        return toString;
    }

    /**
     * Méthode pour obtenir le type d'une String
     * @param toString le type à obtenir
     * @return {@link Type} si le type existe; <code>null</code> sinon
     * @return {@link null}
     */
    public static Type getType(String toString) {
        for (Type type : Type.values()) {
            if (type.toString.equals(toString)) {
                return type;
            }
        }
        return null;
    }
}
