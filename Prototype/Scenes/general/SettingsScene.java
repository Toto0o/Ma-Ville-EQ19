package prototype.scenes.general;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;
import prototype.scenes.Scenes;

public class SettingsScene extends Scenes {

    private Button addHoursPreference, saveHoursPreferece, addNotificationPreference, saveNotificationPreference, infoSettings,menu, back;

    private HBox notificationBox;
    private VBox buttonBox, hoursBox;

    private TextField hourStart, hourEnd;
    private Text hourStartText, hourEndText;

    public SettingsScene(SceneController sceneController) {
        super(sceneController);

        this.addHoursPreference = new Button("Ajouter une préférence d'horaire");
        this.saveHoursPreferece = new Button("Enregistrer mes préférences");
        this.addNotificationPreference = new Button("Personnaliser mes notifications");
        this.saveNotificationPreference = new Button("Enregistrer mes préférences");
        this.menu = new Button("Menu");
        this.back = new Button("Retour");
        this.infoSettings = new Button("Modifier mes informations");

        this.buttonBox = new VBox();
        this.hoursBox = new VBox();
        this.notificationBox = new HBox();

        this.hourStart = new TextField();
        this.hourEnd = new TextField();
        this.hourStartText = new Text("Heure de début (HH:MM)");
        this.hourEndText = new Text("Heure de fin (HH:MM)");

    }

    public void setScene() {
        this.root.setTop(this.menu);
        this.root.setCenter(this.buttonBox);
        this.buttonBox.setAlignment(Pos.CENTER);
        this.buttonBox.setSpacing(30);
        this.buttonBox.getChildren().addAll(this.addHoursPreference, this.addNotificationPreference, this.infoSettings);

        this.hoursBox.getChildren().addAll(
            this.hourStartText,
            this.hourStart,
            this.hourEndText,
            this.hourEnd,
            this.saveHoursPreferece,
            this.back
        );
        this.hourEnd.setMaxWidth(50);
        this.hourStart.setMaxWidth(50);
        this.hoursBox.setSpacing(15);
        this.hoursBox.setAlignment(Pos.TOP_CENTER);

        this.notificationBox.getChildren().addAll(
            this.saveNotificationPreference
        );

        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });

        this.addHoursPreference.setOnMouseClicked((addHoursAction) -> {
            this.root.setCenter(this.hoursBox);
        });

        this.back.setOnMouseClicked((backAction) -> {
            this.root.setCenter(this.buttonBox);
        });

        this.saveHoursPreferece.setOnMouseClicked((saveHoursAction) -> {

        });

        this.addNotificationPreference.setOnMouseClicked((notificationPreferenceAction) -> {
            this.sceneController.newScene("notificationSettings");
        });

        this.infoSettings.setOnMouseClicked((infoSettingsAction) -> {
            this.sceneController.newScene("infoSettings");
        });




    }

}
