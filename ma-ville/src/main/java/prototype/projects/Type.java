package prototype.projects;

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

    public String toString() {
        return toString;
    }

    public static Type getType(String toString) {
        for (Type type : Type.values()) {
            if (type.toString.equals(toString)) {
                return type;
            }
        }
        return null;
    }
}
