package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.Button.AttackButton;
import it.unicam.cs.mpgc.rpg129072.controllers.CombatController;
import it.unicam.cs.mpgc.rpg129072.models.CombatManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

public class CombatScreen extends Canvas implements Renderable {

    private final CombatController combatController;
    private final CombatManager combatManager;
    private final GraphicsContext gc;

    private final Image backgroundImage;
    private final Image playerImage;
    private final Image enemyImage;

    public CombatScreen(CombatController combatController, CombatManager combatManager, int screen_width, int screen_height) {
        super(screen_width, screen_height);
        this.combatController = combatController;
        this.combatManager = combatManager;
        this.gc = this.getGraphicsContext2D();

        this.backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/background/combat-background.jpg")));

        this.playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/characters/combat/elf-lord.png")));
        this.enemyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/characters/combat/merfolk.png")));

        this.render();
    }

    public void render() {
        this.gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        this.gc.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight());

        this.gc.drawImage(this.playerImage, 400, 400, 150, 150);

        this.gc.drawImage(this.enemyImage, 800, 350, 150, 150);
    }

    @Override
    public Scene generateScene() {
        AnchorPane uiLayer = this.createUiLayer();
        StackPane root = new StackPane(this, uiLayer);
        return new Scene(root);
    }

    public AnchorPane createUiLayer(){
        AnchorPane uiLayer = new AnchorPane();
        uiLayer.setPickOnBounds(false);

        // --- Top UI: Health Bars and Names ---

        // Player HUD anchored to the top-left
        VBox playerHud = createHudBox("Player Name", 1.0);
        AnchorPane.setTopAnchor(playerHud, 20.0);
        AnchorPane.setLeftAnchor(playerHud, 20.0);

        // Enemy HUD anchored to the top-right
        VBox enemyHud = createHudBox("Enemy Name", 1.0);
        AnchorPane.setTopAnchor(enemyHud, 20.0);
        AnchorPane.setRightAnchor(enemyHud, 20.0);

        // --- Bottom UI: Action Buttons ---
        VBox actionMenu = new VBox(15);
        actionMenu.setAlignment(Pos.CENTER_LEFT);

        Button buttonA = new AttackButton("A", null);
        Button buttonB =  new AttackButton("B", null);
        Button buttonC =  new AttackButton("C", null);

        actionMenu.getChildren().addAll(buttonA, buttonB, buttonC);

        // Anchor the action buttons to the bottom-left corner
        AnchorPane.setBottomAnchor(actionMenu, 30.0);
        AnchorPane.setLeftAnchor(actionMenu, 30.0);

        uiLayer.getChildren().addAll(playerHud, enemyHud, actionMenu);

        return uiLayer;
    }

    /**
     * Helper method to generate standard HUD boxes for characters.
     */
    private VBox createHudBox(String name, double healthRatio) {
        VBox hud = new VBox(5);

        hud.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Text characterName = new Text(name);
        characterName.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ProgressBar healthBar = new ProgressBar(healthRatio);
        healthBar.setPrefWidth(150);
        healthBar.setStyle("-fx-accent: #32CD32;");

        hud.getChildren().addAll(characterName, healthBar);
        return hud;
    }


}