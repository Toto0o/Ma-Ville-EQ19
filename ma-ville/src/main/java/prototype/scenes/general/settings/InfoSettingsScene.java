package prototype.scenes.general.settings;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;
import prototype.users.UserSession;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Scene de modification des informations du profil
 *
 * <p>Enregistre les champs modifiés avec {@link ApiController#updateUserInfo(String, HashMap)}</p>
 *
 * <p>Accessible par {@link SettingsScene}</p>
 */
public class InfoSettingsScene extends Scenes {

    private TextField name, lastname, password, email, phone, street, streetNumber, postalCode;
    private DatePicker birthday;
    private VBox Vbox;
    private Button back, save;
    private ApiController apiController;

    public InfoSettingsScene(SceneController sceneController) {
        super(sceneController);
        this.Vbox = new VBox();

        this.name = new TextField(UserSession.getInstance().getUser().getName());
        this.name.setId("name");
        this.lastname = new TextField(UserSession.getInstance().getUser().getLastname());
        this.lastname.setId("lastname");
        this.password = new TextField(UserSession.getInstance().getUser().getPassword());
        this.password.setId("password");
        this.birthday = new DatePicker(LocalDate.parse(UserSession.getInstance().getUser().getBirthday()));
        this.birthday.setId("birthday");
        this.email = new TextField(UserSession.getInstance().getUser().getEmail());
        this.email.setId("email");
        this.phone = new TextField(UserSession.getInstance().getUser().getPhone());
        this.phone.setId("phone");
        this.street = new TextField(UserSession.getInstance().getUser().getAddress().getStreet());
        this.street.setId("street");
        this.streetNumber = new TextField(UserSession.getInstance().getUser().getAddress().getNumber());
        this.streetNumber.setId("streetNumber");
        this.postalCode = new TextField(UserSession.getInstance().getUser().getAddress().getPostalCode());
        this.postalCode.setId("postalCode");

        this.back = new Button("Retour");
        this.save = new Button("Enregistrer");



    }

    @Override
    public void setScene() {
        this.root.setTop(back);
        this.Vbox.getChildren().addAll(
                labelText("Prénom :"), this.name,
                labelText("Nom :"), this.lastname,
                labelText("Mot de passe "), this.password,
                labelText("Date de naissance : "), this.birthday,
                labelText("Email : "), this.email,
                labelText("Phone : "), this.phone,
                labelText("Numéro de la résidence :"), this.streetNumber,
                labelText("Rue :"), this.street,
                labelText("Code postal :"), this.postalCode,
                save
        );
        this.Vbox.setSpacing(10);
        this.Vbox.setAlignment(Pos.CENTER);
        this.root.setCenter(this.Vbox);

        this.name.setMaxWidth(250);
        this.lastname.setMaxWidth(250);
        this.password.setMaxWidth(250);
        this.email.setMaxWidth(250);
        this.phone.setMaxWidth(250);
        this.street.setMaxWidth(250);
        this.streetNumber.setMaxWidth(250);
        this.postalCode.setMaxWidth(250);

        this.back.setOnAction(backAction -> {
            this.sceneController.newScene("settings");
        });

        this.save.setOnAction(saveAction -> {
            HashMap<String, String> changes = new HashMap<>();

            for (javafx.scene.Node node : this.Vbox.getChildren()) {
                if (node instanceof TextField field) {
                    if (field.getText() != null) {
                        changes.put(field.getId(), field.getText());
                    }
                }
            }
            this.sceneController.getApiController().updateUserInfo(UserSession.getInstance().getUserId(), changes);
        });
    }
}
