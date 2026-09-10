package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class MapController extends Controller {

    private double playerX = 100;
    private double playerY = 100;
    private final double playerSize = 32;
    private final double playerSpeed = 16;

    private String playerName = "Hero";
    private int playerScore = 0;

    private final int tileSize = 64;
    private final int columns = 15;
    private final int rows = 10;
    private final boolean[][] crushedWheatMap;

    private final double mapWidth = columns * tileSize;
    private final double mapHeight = rows * tileSize;

    public MapController(Stage stage) {
        super(stage);
        this.crushedWheatMap = new boolean[columns][rows];

        // Initial crush where the player spawns
        crushWheatAtPosition(playerX + playerSize / 2, playerY + playerSize / 2);
    }

    /**
     * Processes keyboard input to move the player.
     * Returns true if the state changed and the screen needs to be re-rendered.
     */
    public boolean handleKeyPress(KeyCode key) {
        boolean moved = false;

        if (key == KeyCode.W || key == KeyCode.UP) {
            this.playerY -= this.playerSpeed;
            moved = true;
        } else if (key == KeyCode.S || key == KeyCode.DOWN) {
            this.playerY += this.playerSpeed;
            moved = true;
        } else if (key == KeyCode.A || key == KeyCode.LEFT) {
            this.playerX -= this.playerSpeed;
            moved = true;
        } else if (key == KeyCode.D || key == KeyCode.RIGHT) {
            this.playerX += this.playerSpeed;
            moved = true;
        }

        if (moved) {
            this.playerX = Math.max(0, Math.min(this.playerX, this.mapWidth - this.playerSize));
            this.playerY = Math.max(0, Math.min(this.playerY, this.mapHeight - this.playerSize));
            crushWheatAtPosition(this.playerX + this.playerSize / 2, this.playerY + this.playerSize / 2);
        }

        return moved;
    }

    private void crushWheatAtPosition(double targetX, double targetY) {
        int gridX = (int) (targetX / tileSize);
        int gridY = (int) (targetY / tileSize);

        if (gridX >= 0 && gridX < columns && gridY >= 0 && gridY < rows) {
            if (!this.crushedWheatMap[gridX][gridY]) {
                this.crushedWheatMap[gridX][gridY] = true;
                this.playerScore += 10;
            }
        }
    }

    public double getPlayerX() { return this.playerX; }
    public double getPlayerY() { return this.playerY; }
    public double getPlayerSize() { return this.playerSize; }

    public String getPlayerName() { return this.playerName; }
    public int getPlayerScore() { return this.playerScore; }

    public int getColumns() { return this.columns; }
    public int getRows() { return this.rows; }
    public int getTileSize() { return this.tileSize; }

    public boolean isWheatCrushed(int x, int y) {
        return this.crushedWheatMap[x][y];
    }
}