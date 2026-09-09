package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.controllers.IntroController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IntroScreen extends StackPane implements Renderable {

    private final IntroController introController;

    private int displayedMessage = 0;
    private Text loreText = new Text();

    public IntroScreen(IntroController introController) {
        this.introController = introController;

        this.setStyle("-fx-background-color: black;");

        String loreMessage = "Long ago, a warrior was charged with protecting\nthis land from unknown entities...\n";

        loreText.setText(loreMessage);
        loreText.setFont(Font.font("Arial", FontPosture.ITALIC, 32));
        loreText.setFill(Color.WHITE);
        loreText.setTextAlignment(TextAlignment.CENTER);
        loreText.setLineSpacing(10);

        Text skipText = new Text("Press SPACE to skip the intro");
        skipText.setFont(Font.font("Arial", 16));
        skipText.setFill(Color.GRAY);

        StackPane.setAlignment(loreText, Pos.CENTER);
        StackPane.setAlignment(skipText, Pos.BOTTOM_CENTER);

        // Add a bottom margin to the skip text
        StackPane.setMargin(skipText, new Insets(0, 0, 30, 0));

        this.getChildren().addAll(loreText, skipText);

        // Listen mouse click
        this.setOnMouseClicked(mouseEvent -> triggerContinue());
    }

    @Override
    public Scene generateScene() {
        Scene introScene = new Scene(this);

        // Listen space bar press
        introScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                triggerContinue();
            }
        });
        return introScene;
    }

    private void triggerContinue() {
        if(displayedMessage == 0){
            loreText.setText("For a thousand years, there was peace and quiet, until a months ago...,\nwhen mysterious creatures appeared in the village's wheat fields...");
            displayedMessage++;
        }else {
            this.introController.finishIntro();
        }
    }
}