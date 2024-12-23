package prototype.users;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Utilisateur} de type résident
 */
public class Resident extends Utilisateur {

    private List<String> selectedQuartiers;  // List to store the selected boroughs

    /**
     * Constructeur vide pour la désérialisation
     */
    public Resident() {
        // Ensure the list is initialized
        this.selectedQuartiers = new ArrayList<>();
    }

    /**
     * Constructeur à l'instanciation
     * @param name le prénom
     * @param lastname le nom de famille
     * @param password le mot de passe
     * @param birthday la date de naissance
     * @param address l'adresse de résidence
     * @param phone le numéro de téléphonne
     * @param email l'adresse courriel
     * @param selectedQuartiers la liste des quartiers sélectionnés
     */
    public Resident(String name, String lastname, String password, String birthday, Address address, String phone, String email, List<String> selectedQuartiers) {
        super(name, lastname, password, birthday, phone, email, address);
        // Initialize with the provided list or an empty list
        this.selectedQuartiers = (selectedQuartiers != null) ? new ArrayList<>(selectedQuartiers) : new ArrayList<>();
    }

    /**
     * Gets the list of selected quartiers. Always returns a non-null list.
     * @return the list of selected quartiers
     */
    public List<String> getSelectedQuartiers() {
        if (this.selectedQuartiers == null) {
            this.selectedQuartiers = new ArrayList<>();
        }
        return this.selectedQuartiers;
    }

    /**
     * Sets the list of selected quartiers.
     * @param selectedQuartiers the list to set
     */
    public void setSelectedQuartiers(List<String> selectedQuartiers) {
        this.selectedQuartiers = (selectedQuartiers != null) ? selectedQuartiers : new ArrayList<>();
    }

    @Override
    public boolean isIntervenant() {
        return false;
    }
}
