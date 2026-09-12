package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.Button.MenuButton;
import it.unicam.cs.mpgc.rpg129072.controllers.GameOverController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOverScreen extends StackPane implements Renderable {

    private final GameOverController gameOverController;

    public GameOverScreen(GameOverController gameOverController) {
        this.gameOverController = gameOverController;

        this.setStyle("-fx-background-color: black;");

        VBox mainContainer = new VBox(40);
        mainContainer.setAlignment(Pos.CENTER);

        Text gameOverTitle = new Text("GAME OVER");
        gameOverTitle.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        gameOverTitle.setFill(Color.RED);
        gameOverTitle.setStroke(Color.DARKRED);
        gameOverTitle.setStrokeWidth(2);

        Text finalScoreText = new Text("Final Score: "+ gameOverController.getPlayerScore());
        finalScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        finalScoreText.setFill(Color.WHITE);

        VBox buttonsContainer = new VBox(15);
        buttonsContainer.setAlignment(Pos.CENTER);

        MenuButton restartButton = new MenuButton("Restart Game", gameOverController::restartGame);
        MenuButton menuButton = new MenuButton("Main Menu", gameOverController::mainMenu);
        MenuButton exitButton = new MenuButton("Exit", gameOverController::quitGame);

        buttonsContainer.getChildren().addAll(restartButton, menuButton, exitButton);
        mainContainer.getChildren().addAll(gameOverTitle, finalScoreText, buttonsContainer);

        this.getChildren().add(mainContainer);
    }

    @Override
    public Scene generateScene() {
        return new Scene(this, 800, 600);
    }
}