package prototype.services;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import javafx.application.Platform;
import prototype.entraves.Entrave;
import prototype.projects.Project;
import prototype.projects.Status;
import prototype.projects.Type;
import prototype.users.UserSession;

/**
 * Connexion Api avec Firebase pour modifier un projet et charger les projets associé à l'intervenant utilisateur en cours
 *
 * <p>Utiliser {@link #getProjects()} pour récupérer les projets</p>
 */
public class IntervenantServices {

    /**
     * Retourne les projets chargés avec {@link #fetchProjectsForCurrentUser()}
     * @return {@link ArrayList}&lt;{@link Project}&gt;
     */
    public void getProjects(ArrayList<Project> projects,Runnable updateDisplayCallBack) {
        fetchProjectsForCurrentUser(projects, updateDisplayCallBack);
    }

    /**
     * Méthode pour charger les projets; filtre les projets et renvoi seulement ceux associés à l'intervenant utilisateur en cours
     * @return {@link ArrayList}&lt;{@link Project}&gt;
     */
    public static void fetchProjectsForCurrentUser(ArrayList<Project> projects, Runnable updateDisplayCallBack) {
        String userId = UserSession.getInstance().getUserId();
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects");
        projectsRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Project project = snapshot.getValue(Project.class);
                        if (project.getUid().equals(userId)) {
                            project.setFirebaseKey(snapshot.getKey());
                            projects.add(project);
                        }

                    }
                Platform.runLater(updateDisplayCallBack);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Failed to fetch projects: " + error.getMessage());
            }
        });
    }

    /**
     * Méthode pour mettre à jours les information d'un projet
     * @param changes {@link HashMap} (id, value) des champs modifiés
     * @param projectKey la clé firebase du projet
     */
    public void saveProjectChanges(Project projet) {
        FirebaseDatabase database = FirebaseDatabase
                .getInstance("https://maville-18aa2-default-rtdb.firebaseio.com/");
        DatabaseReference projectsRef = database.getReference("projects").child(projet.getFirebaseKey());
        projectsRef.setValueAsync(projet);
    }


}
