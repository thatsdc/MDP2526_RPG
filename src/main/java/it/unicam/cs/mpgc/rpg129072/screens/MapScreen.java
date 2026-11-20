package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapEnemy;
import it.unicam.cs.mpgc.rpg129072.controllers.MapController;
import it.unicam.cs.mpgc.rpg129072.interfaces.MapState;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

public class MapScreen extends Canvas implements Renderable {

    private final MapController mapController;
    private final MapState mapManager;

    private final GraphicsContext gc;

    private final Image wheat;
    private final Image trampledWheat;

    public MapScreen(MapController mapController, MapState mapManager) {
        super(mapManager.getColumns() * mapManager.getTileSize(),
                mapManager.getRows() * mapManager.getTileSize());

        this.mapController = mapController;
        this.mapManager = mapManager;

        this.gc = this.getGraphicsContext2D();

        this.wheat = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/map_tiles/wheat_tile.png"), "Missing wheat tile"));
        this.trampledWheat = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/map_tiles/wheat_tile_trampled.png"), "Missing trampled wheat tile"));

        this.render();
    }

    public void render() {
        int tileSize = mapManager.getTileSize();

        // Clear the entire canvas
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());

        // Subtle border for tiles
        gc.setStroke(Color.rgb(0, 0, 0, 0.1));

        // Render the wheat map
        for (int x = 0; x < mapManager.getColumns(); x++) {
            for (int y = 0; y < mapManager.getRows(); y++) {
                Image tileToDraw = mapManager.isWheatCrushed(x, y) ? this.trampledWheat : this.wheat;
                gc.drawImage(tileToDraw, x * tileSize, y * tileSize, tileSize, tileSize);
                gc.strokeRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }

        mapManager.getPlayer().render(gc);

        MapEnemy enemy = mapManager.getVisibleEnemy();
        if (enemy != null) {
            enemy.render(gc);
        }
    }

    private AnchorPane createUiLayer() {
        AnchorPane uiLayer = new AnchorPane();
        uiLayer.setPickOnBounds(false);

        // Setup Player Name Text
        Text playerTextElement = new Text("Player: " + mapManager.getPlayerName());
        playerTextElement.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        playerTextElement.setFill(Color.WHITE);
        playerTextElement.setStroke(Color.BLACK);

        // Setup Score Text
        Text scoreTextElement = new Text("Score: " + mapManager.getPlayerScore());
        scoreTextElement.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        scoreTextElement.setFill(Color.WHITE);
        scoreTextElement.setStroke(Color.BLACK);

        // Anchor the elements to the corners with a 15px margin
        AnchorPane.setTopAnchor(playerTextElement, 15.0);
        AnchorPane.setLeftAnchor(playerTextElement, 15.0);

        AnchorPane.setTopAnchor(scoreTextElement, 15.0);
        AnchorPane.setRightAnchor(scoreTextElement, 15.0);

        uiLayer.getChildren().addAll(playerTextElement, scoreTextElement);

        return uiLayer;
    }

    @Override
    public Scene generateScene() {
        AnchorPane uiLayer = this.createUiLayer();

        StackPane root = new StackPane(this, uiLayer);
        root.setStyle("-fx-background-color: black;");

        Scene mapScene = new Scene(root);

        // Handle player movement
        mapScene.setOnKeyPressed(event -> {
            if (mapController.handleKeyPress(event.getCode())) {
                this.render();
            }
        });

        return mapScene;
    }
}