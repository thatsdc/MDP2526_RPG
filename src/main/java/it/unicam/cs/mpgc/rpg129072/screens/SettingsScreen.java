package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.Button.SettingsButton;
import it.unicam.cs.mpgc.rpg129072.controllers.SettingsController;
import it.unicam.cs.mpgc.rpg129072.managers.SettingsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SettingsScreen extends StackPane implements Renderable {

    private SettingsManager settingsManager;
    private SettingsController settingsController;

    public SettingsScreen(SettingsController settingsController, SettingsManager settingsManager) {
        this.settingsController = settingsController;
        this.settingsManager = settingsManager;

        this.setStyle("-fx-background-color: black;");

        VBox mainContainer = new VBox(40);
        mainContainer.setAlignment(Pos.CENTER);

        Text settingsTitle = new Text("SETTINGS");
        settingsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        settingsTitle.setFill(Color.WHITE);

        HBox resolutionContainer = new HBox(20);
        resolutionContainer.setAlignment(Pos.CENTER);

        Text resolutionLabel = new Text("Resolution:");
        resolutionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        resolutionLabel.setFill(Color.WHITE);

        ObservableList<String> resolutionOptions = FXCollections.observableArrayList(
                this.settingsManager.getAvailableResolutions()
        );
        ComboBox<String> resolutionDropdown = new ComboBox<>(resolutionOptions);
        resolutionDropdown.getSelectionModel().select(this.settingsManager.getCurrentResolution());
        resolutionDropdown.setStyle("-fx-font-size: 16px; -fx-cursor: hand;");

        resolutionContainer.getChildren().addAll(resolutionLabel, resolutionDropdown);

        HBox buttonsContainer = new HBox(30);
        buttonsContainer.setAlignment(Pos.CENTER);

        Button applyButton = new SettingsButton("Apply", () -> {
            String selectedResolution = resolutionDropdown.getValue();
            this.settingsManager.setScreenResolution(selectedResolution);
        });

        Button backButton = new SettingsButton("Back", () -> {
            this.settingsController.backToMenu();
        });

        buttonsContainer.getChildren().addAll(backButton, applyButton);

        mainContainer.getChildren().addAll(settingsTitle, resolutionContainer, buttonsContainer);
        this.getChildren().add(mainContainer);
    }


    @Override
    public Scene generateScene() {
        return new Scene(this, 800, 600);
    }
}