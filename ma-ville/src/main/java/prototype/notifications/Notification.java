package prototype.notifications;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import prototype.projects.Project;

public class Notification {

    private Text title, description;
    private VBox vBox;
    private Project project;

    public Notification(Project project) {
        this.project = project;
        this.title = new Text();
        this.description = new Text();
        this.vBox = new VBox();
    }

    private void setUP() {
        this.vBox.getChildren().addAll(
            this.title,
            this.description
        );
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public VBox getNotification() {
        return this.vBox;
    }
}
