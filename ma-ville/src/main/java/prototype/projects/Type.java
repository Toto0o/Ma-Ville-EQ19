package prototype.projects;

/**
 * Type des travaux et projets
 */
public enum Type {

    AUTRE("Autre"),
    EGOUT_ET_AQUEDUC_EXCAVATION("Égouts et queducs - Excavation"),
    TOITURE_NETTOYAGE("Toiture - Nettoyage"),
    TOITURE_RENOVATION("Toiture - Rénovation"),
    TRAVAUX_ROUTIERS("Réseaux routier - Réfection et travaux corrélatifs"),
    EGOUT_AQUEDUC_NETTOYAGE("Égouts et aqueducs - Inspection et nettoyage"),
    CONSTRUCTION_RENOVATION_AVEC("Construction/rénovation avec excavation"),
    CONSTRUCTION_RENOVATION_SANS("Construction/rénovation sans excavation"),
    ENTRETIEN_PAYSAGER("Autre"),
    TRAVAUX_TRANSPORTS_COMMUN("Autre"),
    TRAVAUX_SIGNALISATION_ECLAIRAGE("Feux de signalisation - Ajout/réparation"),
    TRAVAUX_SOUTERRAINS_S3("S-3 Infrastructure souterraine majeure - Puits d'accès"),
    TRAVAUX_SOUTERRAINS_S2("S-2 Infrastructure souterraine mineure ou équipement hors-sol - Réseaux électriques, télécommunications ou câbles des distributions"),
    TRAVAIL_RESIDENTIEL("Travail résidentiel"),
    RESEAUX_GAZ("S-2 Infrastructure souterraine mineure ou équipement hors-sol - Réseaux de gaz"),
    ENTRETIEN_URBAIN("Entretien urbain"),
    ENTRETIEN("Entretien"),
    RESEAUX_ROUTIER_REFECTION_CORRELATIFS("Réseaux routier - Réfection et travaux corrélatifs "),
    ENTRETIEN_RESEAUX_TELECOMMUNICATION("Autre");

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
