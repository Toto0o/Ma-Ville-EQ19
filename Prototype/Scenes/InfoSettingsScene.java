package Prototype.Scenes;

import Prototype.Controllers.SceneController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jdk.incubator.vector.VectorOperators;

public class InfoSettingsScene extends Scenes {

    private TextField username, password, email, dateNaissance, phone, addresse;
    private Text usernameText, passwordText, emailText, phoneText, addresseText;
    private VBox Vbox;
    private Button back, save;

    public InfoSettingsScene(SceneController sceneController) {
        super(sceneController);
        this.Vbox = new VBox();
        this.usernameText = new Text("Username: ");
        this.passwordText = new Text("Password: ");
        this.emailText = new Text("Email: ");
        this.phoneText = new Text("Phone: ");
        this.addresseText = new Text("Address: ");

        this.username = new TextField();
        this.password = new TextField();
        this.email = new TextField();
        this.phone = new TextField();
        this.addresse = new TextField();

        this.back = new Button("Retour");
        this.save = new Button("Enregistrer");

    }

    @Override
    public void setScene() {
        this.Vbox.getChildren().addAll(
                this.usernameText,
                this.username,
                this.passwordText,
                this.password,
                this.emailText,
                this.email,
                this.phoneText,
                this.phone,
                this.addresseText,
                this.addresse,
                this.save,
                this.back
        );
        this.Vbox.setSpacing(10);
        this.Vbox.setAlignment(Pos.CENTER);
        this.root.setCenter(this.Vbox);

        this.username.setMaxWidth(250);
        this.password.setMaxWidth(250);
        this.email.setMaxWidth(250);
        this.phone.setMaxWidth(250);
        this.addresse.setMaxWidth(250);

        this.back.setOnAction(backAction -> {
            this.sceneController.newScene("settings");
        });
    }
}
