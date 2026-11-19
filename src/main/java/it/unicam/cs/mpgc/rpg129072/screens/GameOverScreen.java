package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.Button.MenuButton;
import it.unicam.cs.mpgc.rpg129072.controllers.GameOverController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOverScreen extends StackPane implements Renderable {

    public GameOverScreen(GameOverController gameOverController) {
        this.setStyle("-fx-background-color: black;");

        VBox mainContainer = new VBox(40);
        mainContainer.setAlignment(Pos.CENTER);

        Text gameOverTitle = new Text("GAME OVER");
        gameOverTitle.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        gameOverTitle.setFill(Color.RED);
        gameOverTitle.setStroke(Color.DARKRED);
        gameOverTitle.setStrokeWidth(2);

        Text finalScoreText = new Text(gameOverController.getPlayerName()
                + "'s final score: " + gameOverController.getPlayerScore());
        finalScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        finalScoreText.setFill(Color.WHITE);

        VBox buttonsContainer = new VBox(15,
                new MenuButton("Restart Game", gameOverController::restartGame),
                new MenuButton("Main Menu", gameOverController::mainMenu),
                new MenuButton("Exit", gameOverController::quitGame)
        );
        buttonsContainer.setAlignment(Pos.CENTER);

        mainContainer.getChildren().addAll(gameOverTitle, finalScoreText, buttonsContainer);
        this.getChildren().add(mainContainer);
    }

    @Override
    public Scene generateScene() {
        return new Scene(this);
    }
}