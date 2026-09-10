package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.MapCharacter;
import it.unicam.cs.mpgc.rpg129072.controllers.MapController;
import it.unicam.cs.mpgc.rpg129072.models.MapManager;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class MapScreen extends Canvas implements Renderable {

    private final MapController mapController;
    private final MapManager mapManager;

    private MapCharacter player;

    private final GraphicsContext gc;

    private final Image wheat;
    private final Image trampledWheat;

    private Text scoreTextElement;
    private Text playerTextElement;

    public MapScreen(MapController mapController, MapManager mapManager) {
        // Set canvas dimensions based on grid size
        int gridWidth = 15;
        int gridHeight = 10;
        int blockSize = 64;
        super(gridWidth * blockSize, gridHeight * blockSize);

        this.mapController = mapController;
        this.mapManager = mapManager;

        this.player = new MapCharacter(
                "/elf-lord.png",
                100, 100,
                16, 16,
                4, 2.5
            );

        this.gc = this.getGraphicsContext2D();

        this.wheat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/wheat_tile.png")));
        this.trampledWheat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/wheat_tile_trampled.png")));

        this.render();
    }

    /**
     * Clears the canvas, draws the wheat field, and then draws the player on top.
     */
    public void render() {
        // Clear the entire canvas
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        // Render the wheat map
        for (int x = 0; x < mapManager.getColumns(); x++) {
            for (int y = 0; y < mapManager.getRows(); y++) {

                int tileSize = mapManager.getTileSize();
                Image tileToDraw = mapManager.isWheatCrushed(x, y) ? this.trampledWheat : this.wheat;
                gc.drawImage(tileToDraw, x * tileSize, y * tileSize, tileSize, tileSize);

                // Subtle border for tiles
                gc.setStroke(Color.rgb(0, 0, 0, 0.1));
                gc.strokeRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }

        this.player.render(gc);
    }

    /**
     * Updates the text values of the UI elements.
     */
    private void updateUI() {
        if (this.scoreTextElement != null && this.playerTextElement != null) {
            this.scoreTextElement.setText("Score: " + mapManager.getPlayerScore());
            this.playerTextElement.setText("Player: " + mapManager.getPlayerName());
        }
    }

    private AnchorPane createUiLayer(){
        AnchorPane uiLayer = new AnchorPane();

        uiLayer.setPickOnBounds(false);

        // Setup Player Name Text
        this.playerTextElement = new Text("Player: " + mapManager.getPlayerName());
        this.playerTextElement.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        this.playerTextElement.setFill(Color.WHITE);
        this.playerTextElement.setStroke(Color.BLACK); // Outline for visibility

        // Setup Score Text
        this.scoreTextElement = new Text("Score: " + mapManager.getPlayerScore());
        this.scoreTextElement.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        this.scoreTextElement.setFill(Color.WHITE);
        this.scoreTextElement.setStroke(Color.BLACK);

        // Anchor the elements to the corners with a 15px margin
        AnchorPane.setTopAnchor(this.playerTextElement, 15.0);
        AnchorPane.setLeftAnchor(this.playerTextElement, 15.0);

        AnchorPane.setTopAnchor(this.scoreTextElement, 15.0);
        AnchorPane.setRightAnchor(this.scoreTextElement, 15.0);

        uiLayer.getChildren().addAll(this.playerTextElement, this.scoreTextElement);

        return uiLayer;
    }

    @Override
    public Scene generateScene() {
        AnchorPane uiLayer = this.createUiLayer();

        StackPane root = new StackPane(this, uiLayer);
        root.setStyle("-fx-background-color: green;");

        Scene mapScene = new Scene(root);

        // Handle player movement
        mapScene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            mapManager.handleKeyPress(key, player);
            this.render();
        });

        return mapScene;
    }
}