package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.controllers.SetNameController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SetNameScreen extends StackPane implements Renderable {

    private static final String INPUT_STYLE = "-fx-background-color: #222222; " +
            "-fx-text-fill: white; " +
            "-fx-border-color: white; " +
            "-fx-border-width: 3px; " +
            "-fx-padding: 10px;";
    private static final String INPUT_ERROR_STYLE = INPUT_STYLE + "-fx-border-color: red;";

    private static final String BUTTON_DEFAULT_STYLE = "-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10 40; -fx-cursor: hand;";
    private static final String BUTTON_HOVER_STYLE = "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10 40; -fx-cursor: hand;";

    private final Button backButton = new Button("← Back");
    private final TextField nameInputField = new TextField();
    private final Button confirmButton = new Button("CONFIRM");

    private final int screenWidth;
    private final int screenHeight;

    public SetNameScreen(SetNameController setNameController, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.setStyle("-fx-background-color: black;");
        this.setPrefSize(screenWidth, screenHeight);

        // --- Back Button Setup ---
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand;");
        backButton.setOnAction(e -> setNameController.goBack());

        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(20, 0, 0, 20));

        // --- Main Content Setup ---
        VBox mainContainer = new VBox(40);
        mainContainer.setAlignment(Pos.CENTER);

        // Prompt title
        Text promptTitle = new Text("ENTER YOUR NAME");
        promptTitle.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        promptTitle.setFill(Color.WHITE);

        // Large input field
        nameInputField.setMaxWidth(350);
        nameInputField.setAlignment(Pos.CENTER);
        nameInputField.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        nameInputField.setStyle(INPUT_STYLE);

        // Block input beyond the maximum length defined by the controller
        int maxLength = setNameController.getMaxNameLength();
        nameInputField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= maxLength ? change : null));

        // Remove the error highlight as soon as the user types again
        nameInputField.textProperty().addListener((obs, oldText, newText) ->
                nameInputField.setStyle(INPUT_STYLE));

        // Confirm button
        confirmButton.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        confirmButton.setStyle(BUTTON_DEFAULT_STYLE);
        confirmButton.setOnMouseEntered(e -> confirmButton.setStyle(BUTTON_HOVER_STYLE));
        confirmButton.setOnMouseExited(e -> confirmButton.setStyle(BUTTON_DEFAULT_STYLE));

        // Same action for the button and for the Enter key
        Runnable submit = () -> {
            if (!setNameController.submitName(nameInputField.getText())) {
                nameInputField.setStyle(INPUT_ERROR_STYLE);
            }
        };
        confirmButton.setOnAction(e -> submit.run());
        nameInputField.setOnAction(e -> submit.run());

        mainContainer.getChildren().addAll(promptTitle, nameInputField, confirmButton);
        this.getChildren().addAll(mainContainer, backButton);
    }

    @Override
    public Scene generateScene() {
        Scene scene = new Scene(this, screenWidth, screenHeight);
        // Give focus to the input once the scene is shown
        Platform.runLater(nameInputField::requestFocus);
        return scene;
    }
}