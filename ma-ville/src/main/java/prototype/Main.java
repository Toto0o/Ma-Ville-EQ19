package prototype;

import prototype.controllers.ApiController;
import prototype.notifications.Notification;
import prototype.projects.Project;
import prototype.projects.Status;
import prototype.projects.Type;
import prototype.services.FirebaseInitialize;
import prototype.services.ServiceSession;
import prototype.users.Address;
import prototype.users.Intervenant;
import prototype.users.Resident;
import prototype.users.UserSession;

/**
 * Classe main pour des fins de construction d'artifact JAR; permet de lancer l'executable
 *
 * <p>Initialise la connexion avec Firebase par {@link FirebaseInitialize#initialize()}</p>
 */
public class Main {

    public static void main(String[] args) {
        FirebaseInitialize.initialize();
        MaVille.main(args);
    }
}
