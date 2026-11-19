package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.Button.MenuButton;
import it.unicam.cs.mpgc.rpg129072.controllers.MenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuScreen extends StackPane implements Renderable {

    public MenuScreen(MenuController menuController) {
        // Main layout for the game menu
        this.setAlignment(Pos.CENTER);

        String gifPath = getClass().getResource("/background/menu_background.gif").toExternalForm();
        ImageView backgroundView = new ImageView(new Image(gifPath));

        // Background always fills the screen, whatever the resolution
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
        backgroundView.setPreserveRatio(false);
        backgroundView.setManaged(false);

        // Main layout for the game menu buttons and title
        VBox menuLayout = new VBox(25);
        menuLayout.setAlignment(Pos.CENTER);

        // Game title
        Text gameTitle = new Text("THE VILLAGE DEFENDER");
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        gameTitle.setFill(Color.WHITE);
        gameTitle.setStroke(Color.BLACK);
        gameTitle.setStrokeWidth(2);

        // Menu buttons
        Button startBtn = new MenuButton("Start Game", menuController::startGame);
        Button optionsBtn = new MenuButton("Options", menuController::openSettings);
        Button exitBtn = new MenuButton("Exit", menuController::quitGame);

        menuLayout.getChildren().addAll(gameTitle, startBtn, optionsBtn, exitBtn);

        this.getChildren().addAll(backgroundView, menuLayout);
    }

    @Override
    public Scene generateScene() {
        return new Scene(this);
    }
}