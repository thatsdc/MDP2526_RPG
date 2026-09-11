package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.Button.MenuButton;
import it.unicam.cs.mpgc.rpg129072.controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MenuScreen extends StackPane implements Renderable {

    public MenuScreen(MenuController menuController) {
        // Main layout for the game menu
        this.setAlignment(Pos.CENTER);

        String gifPath = getClass().getResource("/menu-screen-background.gif").toExternalForm();
        Image backgroundGif = new Image(gifPath);
        ImageView backgroundView = new ImageView(backgroundGif);

        // Bind the image dimensions to the stage
        backgroundView.fitWidthProperty().bind(menuController.getStage().widthProperty());
        backgroundView.fitHeightProperty().bind(menuController.getStage().heightProperty());
        backgroundView.setPreserveRatio(false);

        // Main layout for the game menu buttons and title
        VBox menuLayout = new VBox(25);
        menuLayout.setAlignment(Pos.CENTER);

        // Game title
        Text gameTitle = new Text("EPIC GAME");
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        gameTitle.setFill(Color.WHITE);
        gameTitle.setStroke(Color.BLACK);
        gameTitle.setStrokeWidth(2);

        // Menu buttons
        Button startBtn = new MenuButton("Start Game", menuController::startGame);
        Button leaderboardBtn = new MenuButton("Leaderboard", menuController::openLeaderboard);
        Button optionsBtn = new MenuButton("Options", menuController::openSettings);
        Button exitBtn = new MenuButton("Exit", menuController::quitGame);

        // Add elements to the menuLayout
        menuLayout.getChildren().addAll(gameTitle, startBtn, leaderboardBtn, optionsBtn, exitBtn);

        // Add backgroundView and menuLayout to the MenuScreen StackPane
        this.getChildren().addAll(backgroundView, menuLayout);
    }

    public Scene generateScene(){
        return new Scene(this);
    }
}
