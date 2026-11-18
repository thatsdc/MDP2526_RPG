package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.controllers.IntroController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IntroScreen extends StackPane implements Renderable {

    private final IntroController introController;
    private final Text loreText = new Text();

    private final int screenWidth;
    private final int screenHeight;

    public IntroScreen(IntroController introController, int screenWidth, int screenHeight) {
        this.introController = introController;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.setStyle("-fx-background-color: black;");
        this.setPrefSize(screenWidth, screenHeight);

        // Lore text, wrapped automatically based on the screen width
        loreText.setText(introController.getCurrentPage());
        loreText.setFont(Font.font("Arial", FontPosture.ITALIC, 32));
        loreText.setFill(Color.WHITE);
        loreText.setTextAlignment(TextAlignment.CENTER);
        loreText.setLineSpacing(10);
        loreText.setWrappingWidth(screenWidth * 0.8);

        // Input hint
        Text hintText = new Text("Click or press SPACE to continue  ·  ESC to skip");
        hintText.setFont(Font.font("Arial", 16));
        hintText.setFill(Color.GRAY);

        StackPane.setAlignment(loreText, Pos.CENTER);
        StackPane.setAlignment(hintText, Pos.BOTTOM_CENTER);
        StackPane.setMargin(hintText, new Insets(0, 0, 30, 0));

        this.getChildren().addAll(loreText, hintText);

        // Mouse click advances to the next page
        this.setOnMouseClicked(mouseEvent -> showNextPage());
    }

    @Override
    public Scene generateScene() {
        Scene introScene = new Scene(this, screenWidth, screenHeight);

        // Key released instead of pressed: holding the key down won't skip several pages
        introScene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case SPACE -> showNextPage();
                case ESCAPE -> introController.skip();
                default -> { }
            }
        });
        return introScene;
    }

    private void showNextPage() {
        if (introController.advance()) {
            loreText.setText(introController.getCurrentPage());
        }
    }
}