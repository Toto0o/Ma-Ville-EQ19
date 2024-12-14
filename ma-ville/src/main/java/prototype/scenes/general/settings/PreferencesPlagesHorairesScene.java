package prototype.scenes.general.settings;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import prototype.scenes.Scenes;
import prototype.controllers.SceneController;
import prototype.users.Horaire;
import prototype.users.UserSession;

public class PreferencesPlagesHorairesScene extends Scenes {

    private VBox vbox;
    private Button submitButton, backButton;
    private Text confirmationText;
    private GridPane grid;
    private HBox mainLayout; // VBox to stack the timetable and legend vertically

    public PreferencesPlagesHorairesScene(SceneController sceneController) {
        super(sceneController);

        this.vbox = new VBox(10);
        this.submitButton = new Button("Soumettre");
        this.confirmationText = labelText("");
        this.grid = new GridPane();
        this.mainLayout = new HBox(20); // Add spacing between timetable and legend

        // Setting the title for the scene
        Text title = labelText("Préférences de Plages Horaires");
        title.setFont(new Font("Arial", 24));

        // Set up the grid for the timetable
        setupGrid();

        // Set up the back button (aligned to the top left)
        this.backButton = new Button("Retour");
        VBox backButtonContainer = new VBox(10, backButton);
        backButtonContainer.setAlignment(Pos.TOP_LEFT);

        // Add the title, back button, and mainLayout to VBox
        vbox.getChildren().addAll(backButtonContainer, title, mainLayout, submitButton, confirmationText);
        vbox.setAlignment(Pos.CENTER);

        // Button action for submit
        submitButton.setOnAction(event -> submitPreferences());

        // Initially hide the submit button
        submitButton.setDisable(true);
    }

    @Override
    public void setScene() {
        this.root.setCenter(this.vbox);

        // Set back button action
        this.backButton.setOnMouseClicked((event) -> {
            this.sceneController.newScene("settings");
        });
    }

    // Method to set up the timetable grid (week schedule with hours from 08:00 to
    // 20:00)
    private void setupGrid() {
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER); // Center grid contents

        String[] days = { "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche" };
        String[] times = { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00" };

        int row = 0;

        // Add time labels to the first column
        for (int i = 0; i < times.length; i++) {
            Text timeText = labelText(times[i]);
            timeText.setFont(new Font("Arial", 14));
            grid.add(timeText, 0, i + 1); // First column for times
        }

        // Add column headers for each day
        for (int i = 0; i < days.length; i++) {
            Text dayText = labelText(days[i]);
            dayText.setFont(new Font("Arial", 14));
            grid.add(dayText, i + 1, row);
        }

        // Loop through each day (columns) and time (rows) to create clickable squares
        for (int i = 0; i < 7; i++) { // 7 days
            for (int j = 0; j < 13; j++) { // 13 time slots (08:00 to 20:00)
                // Create a rectangle (square) for each time slot
                Rectangle timeSlot = new Rectangle(150, 50);

                if (UserSession.getInstance().getUser().getHoraire().get(i, j)) {
                timeSlot.setFill(Color.LIGHTGREEN);}
                else { timeSlot.setFill(Color.LIGHTGRAY);}
                timeSlot.setStroke(Color.BLACK);

                // Set up behavior for the clickable squares

                timeSlot.setOnMouseClicked(event -> toggleTimeSlot(timeSlot));
                timeSlot.setId(i + "_" + j);

                // Mark some slots as unclickable (in light red)
                if (shouldBeUnselectable(i, j)) {
                    timeSlot.setFill(Color.LIGHTCORAL);
                    timeSlot.setDisable(true); // Disable clicking
                }

                grid.add(timeSlot, i + 1, j + 1);
            }
        }

        // Create the legend for the timetable (below the grid)
        VBox legendBox = new VBox(10);
        legendBox.setAlignment(Pos.CENTER); // Center the legend

        Text legendTitle = labelText("Légende");
        legendTitle.setFont(new Font("Arial", 16));

        // Create small colored squares for the legend
        HBox redBox = new HBox(10);
        redBox.setAlignment(Pos.CENTER_LEFT); // Center items within the HBox
        Rectangle redSquare = new Rectangle(20, 20, Color.RED);
        redBox.getChildren().addAll(redSquare, labelText("Occupé"));

        HBox whiteBox = new HBox(10);
        whiteBox.setAlignment(Pos.CENTER_LEFT); // Center items within the HBox
        Rectangle whiteSquare = new Rectangle(20, 20, Color.LIGHTGRAY);
        whiteSquare.setStroke(Color.BLACK); // Add a border to make it visible
        whiteBox.getChildren().addAll(whiteSquare, labelText("Disponible"));

        legendBox.getChildren().addAll(legendTitle, redBox, whiteBox);

        // Add timetable and legend to the mainLayout (VBox)
        mainLayout.getChildren().addAll(grid, legendBox); // Stack timetable and legend
        mainLayout.setAlignment(Pos.CENTER); // Center the entire layout
    }

    // Method to toggle the background color of a time slot when clicked
    private void toggleTimeSlot(Rectangle timeSlot) {
        // If already selected, unselect (turn back to light gray)
        if (timeSlot.getFill() == Color.LIGHTGREEN) {
            timeSlot.setFill(Color.LIGHTGRAY);
        } else {
            // Otherwise, mark as selected (turn to light green)
            timeSlot.setFill(Color.LIGHTGREEN);
        }

        // Enable submit button once a selection is made
        submitButton.setDisable(false);
    }

    // Check whether the time slot should be unclickable (e.g., for certain hours)
    private boolean shouldBeUnselectable(int day, int hour) {
        // For example, here we disable the 12:00 to 14:00 slots for all days
        return hour >= 4 && hour <= 5; // Disable 12:00 to 14:00 slots (indexed from 0)
    }

    // Method to handle submitting the preferences
    private void submitPreferences() {
        // Show a confirmation message when the user submits the preferences
        Horaire horaire = new Horaire();

        for (javafx.scene.Node preference : grid.getChildren()) {
            if (preference instanceof Rectangle rectangle) {
                if (rectangle.getFill() == Color.LIGHTGREEN) {
                    String[] id = rectangle.getId().split("_");
                    int day = Integer.parseInt(id[0]);
                    int hour = Integer.parseInt(id[1]);
                    horaire.set(day, hour, true);
                }
            }

        }
        UserSession.getInstance().getUser().setHoraire(horaire);
        confirmationText.setText("Préférence de plages horaires soumise.");
    }
}
