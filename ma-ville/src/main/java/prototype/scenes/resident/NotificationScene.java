package prototype.scenes.resident;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.controllers.ApiController;
import prototype.controllers.SceneController;
import prototype.notifications.Notification;
import prototype.scenes.Scenes;
import prototype.services.FirebaseCallback;
import prototype.services.ServiceSession;

import java.util.ArrayList;

public class NotificationScene extends Scenes {

    private VBox vbox;
    private Button menu;
    private Text nonLu;
    private ApiController apiController;

    public NotificationScene(SceneController sceneController) {
        super(sceneController);
        apiController = ServiceSession.getInstance().getController();
    }

    @Override
    public void setScene() {
        this.vbox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        this.nonLu = new Text();

        this.menu = new Button("Menu");
        this.root.setTop(menu);
        this.root.setCenter(scrollPane);

        try {
            FirebaseCallback<ArrayList<Notification>> callback = new FirebaseCallback<>() {
                @Override
                public void onSucessReturn(ArrayList<Notification> notifications) {
                    Platform.runLater(() -> {
                        int nnlu = 0;
                        for (Notification notification : notifications) {
                            vbox.getChildren().add(notification.afficher());
                            if (!notification.isLu()) nnlu++;
                            nonLu.setText(nnlu + " nouvelles notifications");
                            vbox.getChildren().addFirst(nonLu);
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                }

                @Override
                public void onSucess() {}
            };

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.menu.setOnAction(e -> newSceneAction(e, "menu"));
    }
}
