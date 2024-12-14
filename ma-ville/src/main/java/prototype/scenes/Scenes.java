package prototype.scenes;

import java.time.format.DateTimeFormatter;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;

public abstract class Scenes {

    protected BorderPane root;
    protected Scene scene;

    protected Button menuButton;
    protected SceneController sceneController;
    protected DateTimeFormatter formatter;

    public Scenes(SceneController sceneController) {
        this.sceneController = sceneController;
        this.root = new BorderPane();
        this.scene = new Scene(this.root);
        this.menuButton = new Button("Menu");
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        root.setStyle("-fx-background-color: linear-gradient(to left, #0000FF, #87CEDA);");
    }

    protected Text labelText(String input) {
        Text text = new Text(input);
        text.setFill(Color.WHITE);
        text.setStyle(String.format("-fx-font-weight: bold;"));
        return text;
    }

    public Scene getScene() {
        return this.scene;
    }

    public abstract void setScene();

    protected void newSceneAction(Event event, String scene) {
        this.sceneController.newScene(scene);
    }

}
