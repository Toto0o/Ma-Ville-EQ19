package prototype.scenes;

import java.time.format.DateTimeFormatter;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import prototype.controllers.SceneController;

/**
 * Abstract scene
 *
 * <p>Défini le constructeur de base pour toutes les scenes</p>
 */
public abstract class Scenes {

    /**
     * Le main layout
     */
    protected BorderPane root;

    /**
     * La scene qui contient les différents {@link javafx.scene.Node Nodes}
     */
    protected Scene scene;

    /**
     * Le bouton de menu accessible par la plupart des scenes
     */
    protected Button menuButton;

    /**
     * Le controlleur de scenes
     */
    protected SceneController sceneController;

    /**
     * Pour formater les dates choisies avec {@link javafx.scene.control.DatePicker DatePicker}
     */
    protected DateTimeFormatter formatter;

    public Scenes(SceneController sceneController) {
        this.sceneController = sceneController;
        this.root = new BorderPane();
        this.scene = new Scene(this.root);
        this.menuButton = new Button("Menu");
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        root.setStyle("-fx-background-color: linear-gradient(to left, #0000FF, #87CEDA);");
    }

    /**
     * Permet aux scenes d'afficher les {@link Text} en blanc
     * @param input le text
     * @return {@link Text} à afficher
     */
    protected Text labelText(String input) {
        Text text = new Text(input);
        text.setFill(Color.WHITE);
        text.setStyle(String.format("-fx-font-weight: bold;"));
        return text;
    }

    public Scene getScene() {
        return this.scene;
    }

    /**
     * Méthode à implementer par les scenes pour mettre en place la scene
     */
    public abstract void setScene();

    /**
     * Méthode permettant de changer de scene
     * @param event l'événement
     * @param scene la nouvelle scene à charger
     */
    protected void newSceneAction(Event event, String scene) {
        this.sceneController.newScene(scene);
    }

}
