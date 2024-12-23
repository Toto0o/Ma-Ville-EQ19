package scenarioTests;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import prototype.MaVille;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.entraves.Entrave;
import prototype.notifications.Notification;
import prototype.projects.Project;
import prototype.projects.Request;
import prototype.scenes.general.consult.ConsultEntraveScene;
import prototype.scenes.general.consult.ConsultProjectScene;
import prototype.scenes.general.consult.SearchProjectsScene;
import prototype.scenes.resident.NotificationScene;
import prototype.scenes.general.login.LoginScene;
import prototype.scenes.general.menu.LaunchScene;
import prototype.scenes.general.register.RegisterScene;
import prototype.scenes.general.register.RoleSelectionScene;
import prototype.scenes.general.settings.InfoSettingsScene;
import prototype.scenes.general.settings.NotificationSettingsScene;
import prototype.scenes.general.settings.PreferencesPlagesHorairesScene;
import prototype.scenes.general.settings.SettingsScene;
import prototype.scenes.intervenant.ConsultRequestsScene;
import prototype.scenes.intervenant.IntervenantProjectsScene;
import prototype.scenes.resident.RequestScene;
import prototype.services.FirebaseCallback;
import prototype.services.RequestService;
import prototype.services.ServiceSession;
import prototype.users.Address;
import prototype.users.Intervenant;
import prototype.users.Resident;
import prototype.users.UserSession;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.setupFixture;
import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;


/**
 * Application de test des scénarios;
 *
 * Simule l'application réelle et vérifie les connexions entre les scenes, ainsi que les fonctionnalité accessible;
 *
 * <p>Le module de connexion {@link ApiController} est simulé avec {@link Mockito#mock(Object[])}</p>
 *
 * <p>Les scenes sont testé en vérifiant l'existance des boutons, champs de text ou autre outil de fonctionnalité.
 * Test ensuite la redirection des boutons vers la scene correspondante ou le changement d'état du système</p>
 *
 *
 */
public class ScenarioTest extends ApplicationTest {

    private SceneController sceneController;
    private ApiController apiController;

    private ArrayList<Project> projects;
    private ArrayList<Entrave> entraves;
    private ArrayList<Request> requests;
    private ArrayList<Notification> notifications;


    private Resident resident;
    private Intervenant intervenant;
    private String name = "name";
    private String lastName = "lastName";
    private String password = "password";
    private String birthday = "2000-01-01";
    private String phone = "phone";
    private String street = "street";
    private String streetNumber = "streetNumber";
    private String postalCode = "postalCode";
    private String quartier = "quartier";
    private String email = "email";
    private String cityId = "cityId";

    /**
     * Démarrage de l'application de test.
     *
     * <p>{@link #setUpEnv()} pour instancier le mock du module de connexion</p>
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        setUpEnv();
        sceneController = new SceneController(stage);
        new MaVille().start(stage);
    }

    /**
     * Crée un {@link Mockito#mock(Object[])} de {@link ApiController} pour simuler une base de donnée fonctionnelle;
     *
     * <p>Instancie des simulations de :
     * <ul>
     *     <li>{@link Project}</li>
     *     <li>{@link Entrave}</li>
     *     <li>{@link Request}</li>
     *     <li>{@link Notification}</li>
     * </ul>
     * Les éléments sont ajoutés dans un {@link ArrayList}
     * </p>
     * <p>Instancie des utilisateurs {@link Resident} et {@link Intervenant}</p>
     * @throws Exception
     */
    private void setUpEnv() throws Exception {
        apiController = mock(ApiController.class);

        projects = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Project project = mock(Project.class);
            projects.add(project);}

        entraves = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Entrave entrave = mock(Entrave.class);
            entraves.add(entrave);}

        requests = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            Request request = mock(Request.class);
            requests.add(request);}

        notifications = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Notification notification = mock(Notification.class);
            notifications.add(notification);}

        FirebaseCallback<ArrayList<Notification>> callback = mock(FirebaseCallback.class);

        RequestService requestService = mock(RequestService.class);
        apiController.setRequestService(requestService);
        doAnswer(invocation -> {
            ArrayList<Request> requests1 = invocation.getArgument(0);
            Runnable updateDisplay = invocation.getArgument(1);
            Request realRequest = new Request();
            realRequest.setTitle("Titre");
            realRequest.setDescription("Description");
            realRequest.setStatus("Pending");
            realRequest.setDate("today");
            realRequest.setQuartier("Quartier");
            realRequest.setStreet("Street");
            requests1.add(realRequest);
            Platform.runLater(updateDisplay);
            return null;
        }).when(requestService).getRequests(any(ArrayList.class), any(Runnable.class));

        Mockito.when(apiController.getProjects()).thenReturn(projects);
        Mockito.when(apiController.getEntraves()).thenReturn(entraves);
        Mockito.when(apiController.getNotifications(callback)).thenReturn(notifications);

        ServiceSession.getInstance().setController(apiController);

        Address address = new Address(streetNumber, street, postalCode);
        address.setBorough(quartier);

        resident = new Resident(
                name,lastName, password, birthday, address, phone, email);
        intervenant = new Intervenant(
                name, lastName, password, birthday, phone, email, address, cityId);


    }

    /**
     * Test de la scene initiale {@link LaunchScene}
     * <p></p>
     * @throws Exception
     */
    @Test
    public void launch() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("launch");
        });
        assertInstanceOf(LaunchScene.class, sceneController.getScene());
        verifyThat("#loginButton", isVisible());
        verifyThat("#registerButton", isVisible());

        clickOn("#loginButton");
        waitForFxEvents();
        assertInstanceOf(LoginScene.class, sceneController.getScene());

        setupFixture(() ->
                sceneController.newScene("launch")
        );
        clickOn("#registerButton");
        waitForFxEvents();
        assertInstanceOf(RoleSelectionScene.class, sceneController.getScene());
    }

    @Test
    public void login() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("login");
        });

        verifyThat("#login", isVisible());

        verifyThat("#username", isVisible());
        verifyThat("#password", isVisible());

        verifyThat("#back", isVisible());

    }
    @Test
    public void roleSelection() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("roleSelection");
        });
        verifyThat("#resident", isVisible());
        verifyThat("#intervenant", isVisible());

        clickOn("#resident");
        waitForFxEvents();
        assertInstanceOf(RegisterScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("roleSelection");
        });
        clickOn("#intervenant");
        waitForFxEvents();
        assertInstanceOf(RegisterScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("roleSelection");
        });
        clickOn("#back");
        waitForFxEvents();
        assertInstanceOf(LaunchScene.class, sceneController.getScene());
    }

    @Test
    public void ResidentRegister() throws Exception {
        UserSession.getInstance().setUser(resident);
        setupFixture(() -> {
            sceneController.newScene("roleSelection");
        });
        clickOn("#resident");
        waitForFxEvents();

        assertInstanceOf(RegisterScene.class, sceneController.getScene());

        verifyThat("#name", isVisible());
        verifyThat("#lastName", isVisible());
        verifyThat("#birthday", isVisible());
        verifyThat("#streetName", isVisible());
        verifyThat("#streetNumber", isVisible());
        verifyThat("#postalCode", isVisible());
        verifyThat("#email", isVisible());
        verifyThat("#phone", isVisible());
        verifyThat("#password1", isVisible());
        verifyThat("#password2", isVisible());
        verifyThat("#submitButton", isVisible());
        verifyThat("#backButton", isVisible());

        clickOn("#name").write(name);
        clickOn("#lastName").write(lastName);
        clickOn("#birthday").write(birthday);
        clickOn("#streetName").write(street);
        clickOn("#streetNumber").write(streetNumber);
        clickOn("#postalCode").write(postalCode);
        clickOn("#email").write(email);
        clickOn("#phone").write(phone);
        clickOn("#password1").write(password);
        clickOn("#password2").write(password);
        clickOn("#submitButton");
        waitForFxEvents();
        assertInstanceOf(LoginScene.class, sceneController.getScene());

    }

    @Test
    public void intervenantRegister() throws Exception {
        UserSession.getInstance().setUser(intervenant);
        setupFixture(() -> {
            sceneController.newScene("roleSelection");
        });
        clickOn("#intervenant");
        waitForFxEvents();

        assertInstanceOf(RegisterScene.class, sceneController.getScene());

        verifyThat("#name", isVisible());
        verifyThat("#lastName", isVisible());
        verifyThat("#birthday", isVisible());
        verifyThat("#streetName", isVisible());
        verifyThat("#streetNumber", isVisible());
        verifyThat("#postalCode", isVisible());
        verifyThat("#email", isVisible());
        verifyThat("#phone", isVisible());
        verifyThat("#password1", isVisible());
        verifyThat("#password2", isVisible());
        verifyThat("#backButton", isVisible());
        verifyThat("#cityID", node -> node.isManaged() && node.isVisible());
        verifyThat("#intervenantSubmitButton", node -> node.isManaged() && node.isVisible());
        verifyThat("#submitButton", node -> !node.isVisible());
    }

    @Test
    public void residentMenu() throws Exception {
        UserSession.getInstance().setUser(resident);
        setupFixture(() -> {
            sceneController.newScene("menu");
        });

        verifyThat("#consultButton", isVisible());
        verifyThat("#entraveButton", isVisible());
        verifyThat("#settingsButton", isVisible());
        verifyThat("#requestButton", isVisible());
        verifyThat("#notificationButton", isVisible());
        verifyThat("#logoutButton", isVisible());

        //Intervenant buttons should not appear
        verifyThat("#intervenantProjectButton", node -> !node.isManaged() && !node.isVisible());
        verifyThat("#consultRequestButton", node -> !node.isManaged() && !node.isVisible());

        //Verify buttons links to classes

        clickOn("#consultButton");
        waitForFxEvents();
        assertInstanceOf(ConsultProjectScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("menu");
        });
        clickOn("#entraveButton");
        waitForFxEvents();
        assertInstanceOf(ConsultEntraveScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("menu");
        });
        clickOn("#settingsButton");
        waitForFxEvents();
        assertInstanceOf(SettingsScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("menu");
        });
        clickOn("#requestButton");
        waitForFxEvents();
        assertInstanceOf(RequestScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("menu");
        });
        clickOn("#notificationButton");
        waitForFxEvents();
        assertInstanceOf(NotificationScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("menu");
        });
        clickOn("#logoutButton");
        waitForFxEvents();
        assertInstanceOf(LaunchScene.class, sceneController.getScene());
    }

    @Test
    public void intervenantMenu() throws Exception{

        UserSession.getInstance().setUser(intervenant);
        setupFixture(() -> {
            sceneController.newScene("menu");
        });

        verifyThat("#consultButton", isVisible());
        verifyThat("#entraveButton", isVisible());
        verifyThat("#settingsButton", isVisible());
        verifyThat("#requestButton", node -> !node.isManaged() && !node.isVisible());
        verifyThat("#logoutButton", isVisible());
        verifyThat("#intervenantProjectButton", node -> node.isManaged() && node.isVisible());
        verifyThat("#consultRequestButton", node -> node.isManaged() && node.isVisible());

        clickOn("#intervenantProjectButton");
        waitForFxEvents();
        assertInstanceOf(IntervenantProjectsScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("menu");
        });
        clickOn("#consultRequestButton");
        waitForFxEvents();
        assertInstanceOf(ConsultRequestsScene.class, sceneController.getScene());
    }

    @Test
    public void settings() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("settings");
        });
        assertInstanceOf(SettingsScene.class, sceneController.getScene());

        verifyThat("#addHoursPreference", isVisible());
        verifyThat("#addNotificationPreference", isVisible());
        verifyThat("#infoSettings", isVisible());
        verifyThat("#menu", isVisible());

        clickOn("#addHoursPreference");
        waitForFxEvents();
        assertInstanceOf(PreferencesPlagesHorairesScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("settings");
        });
        clickOn("#addNotificationPreference");
        waitForFxEvents();
        assertInstanceOf(NotificationSettingsScene.class, sceneController.getScene());

        setupFixture(() -> {
            sceneController.newScene("settings");
        });
        clickOn("#infoSettings");
        waitForFxEvents();
        assertInstanceOf(InfoSettingsScene.class, sceneController.getScene());

    }

    @Test
    public void consultProject() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("consultProjects");
        });

        verifyThat("#boroughFilterCombo", isVisible());
        verifyThat("#typeOfWorkFilterCombo", isVisible());

        ArrayList<VBox> projectsContainer = new ArrayList<>();
        for (Project project : projects) {
            projectsContainer.add(project.afficher());
        }
        ListView<VBox> listview = lookup("#projectListView").query();
        for (VBox expected : listview.getItems()) {
            assertTrue(projectsContainer.contains(expected));
        }
    }

    @Test
    public void consultEntraves() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("consultEntraves");
        });

        verifyThat("#entraveListView", isVisible());

        ArrayList<VBox> entravesContainer = new ArrayList<>();
        for (Entrave entrave : entraves) {
            entravesContainer.add(entrave.afficher());
        }
        ListView<VBox> listview = lookup("#entraveListView").query();
        for (VBox expected : listview.getItems()) {
            assertTrue(entravesContainer.contains(expected));
        }
    }

    @Test
    public void makeRequest() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("request");
        });
        verifyThat("#menu", isVisible());
        verifyThat("#newRequest", isVisible());

        clickOn("#newRequest");
        waitForFxEvents();

        verifyThat("#requestBox", isVisible());
        verifyThat("#title", isVisible());
        verifyThat("#description", isVisible());
        verifyThat("#date", isVisible());
        verifyThat("#sendRequest", isVisible());
    }

    @Test
    public void intervenantConsultProject() throws Exception {
        String userid = "testUid";
        Project expected = projects.get(0);
        expected.setUid(userid);
        intervenant.setId(userid);
        UserSession.getInstance().setUserId(userid);
        UserSession.getInstance().setUser(intervenant);

        setupFixture(() -> {
            sceneController.newScene("intervenantProject");
        });

        assertInstanceOf(IntervenantProjectsScene.class, sceneController.getScene());
        verifyThat("#menu", isVisible());

        VBox vbox = lookup("#projectView").query();
        for (javafx.scene.Node children : vbox.getChildren()) {
            if (children instanceof javafx.scene.layout.VBox projectBox) {
                verifyThat("#title", isVisible());
                verifyThat("#description", isVisible());
                verifyThat("#type", isVisible());
                verifyThat("#quartiers", isVisible());
                verifyThat("#roads", isVisible());
                verifyThat("#startDate", isVisible());
                verifyThat("#endDate", isVisible());
                verifyThat("#horaireTravaux", isVisible());
            }
        }
    }

    @Test
    public void consultRequest() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("consultRequest");
        });

        verifyThat("#menu", isVisible());
        verifyThat("#typeFilterComboBox", isVisible());
        verifyThat("#dateFilterPicker", isVisible());
        verifyThat("#quartierSearchField", isVisible());

        clickOn("#applyFilters");
        waitForFxEvents();

        /*clickOn("#candidature");
        waitForFxEvents();
        verifyThat("#startDate", isVisible());
        verifyThat("#endDate", isVisible());
        verifyThat("#soumettre", isVisible());
        verifyThat("#annuler", isVisible());*/
    }

    @Test
    public void searchProject() throws Exception {
        setupFixture(() -> {
            sceneController.newScene("consultProjects");
        });
        clickOn("#searchButton");
        waitForFxEvents();
        assertInstanceOf(SearchProjectsScene.class, sceneController.getScene());

        verifyThat("#retour", isVisible());
        verifyThat("#search-title", isVisible());
        verifyThat("#search-borough", isVisible());
        verifyThat("#type-filter", isVisible());

        clickOn("#retour");
        waitForFxEvents();
        assertInstanceOf(ConsultProjectScene.class, sceneController.getScene());
    }
}
