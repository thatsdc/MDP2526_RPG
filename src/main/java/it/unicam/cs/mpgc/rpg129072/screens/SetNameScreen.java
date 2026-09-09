package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.controllers.SetNameController;
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
import java.util.function.UnaryOperator;

public class SetNameScreen extends StackPane implements Renderable {

    private final Button backButton = new Button("← Back");
    private final TextField nameInputField = new TextField();
    private final Button confirmButton = new Button("CONFIRM");

    public SetNameScreen(SetNameController setNameController) {
        this.setStyle("-fx-background-color: black;");

        // --- Back Button Setup ---
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        String backStyle = "-fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand;";
        backButton.setStyle(backStyle);

        // Handle the back action
        backButton.setOnAction(e -> {
            setNameController.goBack();
        });

        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(20, 0, 0, 20));

        // --- Main Content Setup ---
        // Main container to stack elements vertically
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

        // Dark theme styling for the text field
        String inputStyle = "-fx-background-color: #222222; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 3px; " +
                "-fx-padding: 10px;";
        nameInputField.setStyle(inputStyle);

        // Restrict input to a maximum of 8 characters
        UnaryOperator<TextFormatter.Change> lengthFilter = change -> {
            if (change.getControlNewText().length() <= 8) {
                return change;
            }
            return null;
        };
        nameInputField.setTextFormatter(new TextFormatter<>(lengthFilter));

        // Confirm button
        confirmButton.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        // Button default and hover styles
        String buttonDefaultStyle = "-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10 40; -fx-cursor: hand;";
        String buttonHoverStyle = "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10 40; -fx-cursor: hand;";

        confirmButton.setStyle(buttonDefaultStyle);
        confirmButton.setOnMouseEntered(e -> confirmButton.setStyle(buttonHoverStyle));
        confirmButton.setOnMouseExited(e -> confirmButton.setStyle(buttonDefaultStyle));

        // Handle the confirm action
        confirmButton.setOnAction(e -> {
            String playerName = nameInputField.getText().trim();
            if (!playerName.isEmpty()) {
                setNameController.submitName(playerName);
            } else {
                nameInputField.setStyle(inputStyle + "-fx-border-color: red;");
            }
        });

        mainContainer.getChildren().addAll(promptTitle, nameInputField, confirmButton);
        this.getChildren().addAll(mainContainer, backButton);
    }

    @Override
    public Scene generateScene() {
        return new Scene(this);
    }
}