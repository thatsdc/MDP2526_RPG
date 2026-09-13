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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import it.unicam.cs.mpgc.rpg129072.enums.ActionType;

import java.util.HashMap;
import java.util.Objects;

public class CombatScreen extends Canvas implements Renderable {

    private final CombatController combatController;
    private final CombatManager combatManager;
    private final GraphicsContext gc;

    private final Image backgroundImage;
    private final Image playerImage;
    private final Image enemyImage;

    private ProgressBar playerHealthBar;
    private Text playerHealthText;
    private ProgressBar enemyHealthBar;
    private Text enemyHealthText;
    private Text combatLogText;

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

        // --- Top UI: Initialize Health Bars and Names ---
        this.playerHealthBar = new ProgressBar();
        this.playerHealthText = new Text();
        VBox playerHud = createHudBox(combatManager.getPlayerName(), playerHealthBar, playerHealthText);
        AnchorPane.setTopAnchor(playerHud, 20.0);
        AnchorPane.setLeftAnchor(playerHud, 20.0);

        this.enemyHealthBar = new ProgressBar();
        this.enemyHealthText = new Text();
        VBox enemyHud = createHudBox(combatManager.getEnemyName(), enemyHealthBar, enemyHealthText);
        AnchorPane.setTopAnchor(enemyHud, 20.0);
        AnchorPane.setRightAnchor(enemyHud, 20.0);

        // --- Bottom Right UI: Combat Log Chronicle ---
        this.combatLogText = new Text();
        this.combatLogText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        this.combatLogText.setFill(Color.WHITE);
        this.combatLogText.setWrappingWidth(350);

        VBox logBox = new VBox(this.combatLogText);
        logBox.setAlignment(Pos.CENTER_LEFT);
        logBox.setPrefSize(380, 80);
        // Dark semi-transparent background for readability
        logBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 15px; -fx-border-color: white; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        AnchorPane.setBottomAnchor(logBox, 30.0);
        AnchorPane.setRightAnchor(logBox, 30.0);

        // Initialize dynamic UI data (health and first log message)
        this.updateDynamicUi();

        // --- Bottom Left UI: Action Buttons ---
        VBox actionMenu = new VBox(15);
        actionMenu.setAlignment(Pos.CENTER_LEFT);

        Button buttonA = new AttackButton("A", () -> {
            combatManager.playTurn(ActionType.ATTACK);
            updateDynamicUi();
            checkIfTheresWinner();
        });
        Button buttonB = new AttackButton("D", () -> {
            combatManager.playTurn(ActionType.DEFENSE);
            updateDynamicUi();
            checkIfTheresWinner();
        });
        Button buttonC = new AttackButton("C", () -> {
            combatManager.playTurn(ActionType.CUNNING);
            updateDynamicUi();
            checkIfTheresWinner();
        });

        actionMenu.getChildren().addAll(buttonA, buttonB, buttonC);
        AnchorPane.setBottomAnchor(actionMenu, 30.0);
        AnchorPane.setLeftAnchor(actionMenu, 30.0);

        uiLayer.getChildren().addAll(playerHud, enemyHud, actionMenu, logBox);
        return uiLayer;
    }

    private void checkIfTheresWinner(){
        HashMap<String, String> payload = new HashMap<>();

        if (combatManager.getPlayerHealth() <= 0){
            payload.put("playerName", combatManager.getPlayerName());
            payload.put("playerScore", Integer.toString(combatManager.getPlayerScore()));
            combatController.combatDefeat(payload);
        }
        else if (combatManager.getEnemyHealth() <= 0){
            payload.put("playerName", combatManager.getPlayerName());
            // Initial enemy health is the score player earns
            payload.put("playerScore", Integer.toString(combatManager.getPlayerScore() + combatManager.getInitialEnemyHealth()));
            combatController.combatWin(payload);
        }
    }

    /**
     * Updates the existing UI nodes (Health bars and Combat Log) with the latest data from the CombatManager.
     */
    private void updateDynamicUi(){
        // Update Player Health
        double playerRatio = (double) combatManager.getPlayerHealth() / combatManager.getInitialPlayerHealth();
        this.playerHealthBar.setProgress(playerRatio);
        this.playerHealthText.setText(Integer.toString(combatManager.getPlayerHealth()));

        // Update Enemy Health
        double enemyRatio = (double) combatManager.getEnemyHealth() / combatManager.getInitialEnemyHealth();
        this.enemyHealthBar.setProgress(enemyRatio);
        this.enemyHealthText.setText(Integer.toString(combatManager.getEnemyHealth()));

        // Update Combat Chronicle Log
        String currentMessage = combatManager.getGameMessage();
        if (currentMessage != null && !currentMessage.isEmpty()) {
            this.combatLogText.setText(currentMessage);
        } else {
            this.combatLogText.setText("A wild " + combatManager.getEnemyName() + " appears! Choose an action.");
        }
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