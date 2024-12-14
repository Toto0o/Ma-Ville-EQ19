package prototype.scenes.general.settings;

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

        this.addHoursPreference = new Button("Modifier mes préférences d'horaire");
        this.addNotificationPreference = new Button("Personnaliser mes notifications");
        this.menu = new Button("Menu");
        this.infoSettings = new Button("Modifier mes informations");

        this.buttonBox = new VBox();

    }

    public void setScene() {
        this.root.setTop(this.menu);
        this.root.setCenter(this.buttonBox);
        this.buttonBox.setAlignment(Pos.CENTER);
        this.buttonBox.setSpacing(30);
        this.buttonBox.getChildren().addAll(this.addHoursPreference, this.addNotificationPreference, this.infoSettings);


        this.menu.setOnMouseClicked((menuAction) -> {
            this.sceneController.newScene("menu");
        });

        this.addHoursPreference.setOnMouseClicked((addHoursAction) -> {
            this.sceneController.newScene("preferencesPlagesHoraires");
        });


        this.addNotificationPreference.setOnMouseClicked((notificationPreferenceAction) -> {
            this.sceneController.newScene("notificationSettings");
        });

        this.infoSettings.setOnMouseClicked((infoSettingsAction) -> {
            this.sceneController.newScene("infoSettings");
        });




    }

}
