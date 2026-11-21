package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.Button.AttackButton;
import it.unicam.cs.mpgc.rpg129072.controllers.CombatController;
import it.unicam.cs.mpgc.rpg129072.enums.ActionType;
import it.unicam.cs.mpgc.rpg129072.interfaces.CombatState;
import javafx.geometry.Insets;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.util.Objects;

public class CombatScreen extends Canvas implements Renderable {

    private final CombatController combatController;
    private final CombatState combatManager;
    private final GraphicsContext gc;

    private final Image backgroundImage;
    private final Image playerImage;
    private final Image enemyImage;

    private final ProgressBar playerHealthBar = new ProgressBar();
    private final Text playerHealthText = new Text();
    private final ProgressBar enemyHealthBar = new ProgressBar();
    private final Text enemyHealthText = new Text();
    private final Text combatLogText = new Text();
    private final VBox actionMenu = new VBox(15);

    public CombatScreen(CombatController combatController, CombatState combatManager) {
        this.combatController = combatController;
        this.combatManager = combatManager;
        this.gc = this.getGraphicsContext2D();

        this.backgroundImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/background/combat_background.jpg"), "Missing combat background"));
        this.playerImage = new Image(this.getCharacterPath("Lord"));
        this.enemyImage = new Image(this.getCharacterPath(combatManager.getEnemy().id()));
    }

    private InputStream getCharacterPath(String name) {
        String path = "/characters/combat/" + name.toLowerCase() + ".png";
        return Objects.requireNonNull(getClass().getResourceAsStream(path), "Missing sprite: " + path);
    }

    /**
     * Draws the background and the character sprites, scaled to the current canvas size.
     */
    private void render() {
        double currentWidth = this.getWidth();
        double currentHeight = this.getHeight();

        this.gc.clearRect(0, 0, currentWidth, currentHeight);
        this.gc.drawImage(this.backgroundImage, 0, 0, currentWidth, currentHeight);

        double spriteSize = currentHeight * 0.25;

        double playerX = currentWidth * 0.30;
        double playerY = currentHeight * 0.55;

        double enemyX = currentWidth * 0.65;
        double enemyY = currentHeight * 0.45;

        this.gc.drawImage(this.playerImage, playerX, playerY, spriteSize, spriteSize);
        this.gc.drawImage(this.enemyImage, enemyX, enemyY, spriteSize, spriteSize);
    }

    @Override
    public Scene generateScene() {
        AnchorPane uiLayer = this.createUiLayer();
        StackPane root = new StackPane(this, uiLayer);

        // The canvas follows the window size, set by the navigation from the settings
        this.widthProperty().bind(root.widthProperty());
        this.heightProperty().bind(root.heightProperty());
        this.widthProperty().addListener(observable -> render());
        this.heightProperty().addListener(observable -> render());

        return new Scene(root);
    }

    private AnchorPane createUiLayer() {
        AnchorPane uiLayer = new AnchorPane();
        uiLayer.setPickOnBounds(false);

        // --- Top UI: Health Bars and Names ---
        VBox playerHud = createHudBox(combatManager.getPlayerName(), playerHealthBar, playerHealthText);
        AnchorPane.setTopAnchor(playerHud, 20.0);
        AnchorPane.setLeftAnchor(playerHud, 20.0);

        VBox enemyHud = createHudBox(combatManager.getEnemyName(), enemyHealthBar, enemyHealthText);
        AnchorPane.setTopAnchor(enemyHud, 20.0);
        AnchorPane.setRightAnchor(enemyHud, 20.0);

        // --- Bottom Right UI: Combat Log Chronicle ---
        combatLogText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        combatLogText.setFill(Color.WHITE);
        combatLogText.setWrappingWidth(350);

        VBox logBox = new VBox(combatLogText);
        logBox.setAlignment(Pos.CENTER_LEFT);
        logBox.setPrefSize(380, 80);
        // Dark semi-transparent background for readability
        logBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 15px; -fx-border-color: white; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        AnchorPane.setBottomAnchor(logBox, 30.0);
        AnchorPane.setRightAnchor(logBox, 30.0);

        // --- Bottom Left UI: Action Buttons ---
        actionMenu.setAlignment(Pos.CENTER_LEFT);

        Button buttonA = new AttackButton("A", () -> {
            combatController.playTurn(ActionType.ATTACK);
            updateDynamicUi();
        });
        Button buttonB = new AttackButton("D", () -> {
            combatController.playTurn(ActionType.DEFENSE);
            updateDynamicUi();
        });
        Button buttonC = new AttackButton("C", () -> {
            combatController.playTurn(ActionType.CUNNING);
            updateDynamicUi();
        });

        actionMenu.getChildren().addAll(buttonA, buttonB, buttonC);
        AnchorPane.setBottomAnchor(actionMenu, 30.0);
        AnchorPane.setLeftAnchor(actionMenu, 30.0);

        // Initialize dynamic UI data (health and first log message)
        this.updateDynamicUi();

        uiLayer.getChildren().addAll(playerHud, enemyHud, actionMenu, logBox);
        return uiLayer;
    }

    /**
     * Updates the existing UI nodes (Health bars, Combat Log and buttons) with the latest combat state.
     */
    private void updateDynamicUi() {
        // Update Player Health
        double playerRatio = (double) combatManager.getPlayerHealth() / combatManager.getInitialPlayerHealth();
        this.playerHealthBar.setProgress(playerRatio);
        this.playerHealthText.setText(Integer.toString(combatManager.getPlayerHealth()));

        // Update Enemy Health
        double enemyRatio = (double) combatManager.getEnemyHealth() / combatManager.getInitialEnemyHealth();
        this.enemyHealthBar.setProgress(enemyRatio);
        this.enemyHealthText.setText(Integer.toString(combatManager.getEnemyHealth()));

        // Update Combat Chronicle Log
        this.combatLogText.setText(combatManager.getGameMessage());

        // No more actions once the combat has ended
        this.actionMenu.setDisable(combatManager.isOver());
    }

    /**
     * Helper method to generate the HUD layout using the provided nodes.
     */
    private VBox createHudBox(String name, ProgressBar healthBar, Text healthText) {
        VBox hud = new VBox(5);
        hud.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Text characterName = new Text(name);
        characterName.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        healthBar.setPrefWidth(150);
        healthBar.setPrefHeight(20);
        healthBar.setStyle("-fx-accent: #32CD32;");

        healthText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        StackPane barContainer = new StackPane();
        barContainer.getChildren().addAll(healthBar, healthText);
        StackPane.setAlignment(healthText, Pos.CENTER_RIGHT);
        StackPane.setMargin(healthText, new Insets(0, 5, 0, 0));

        hud.getChildren().addAll(characterName, barContainer);
        return hud;
    }
}