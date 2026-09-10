package it.unicam.cs.mpgc.rpg129072.models;

import it.unicam.cs.mpgc.rpg129072.components.MapCharacter;
import javafx.scene.input.KeyCode;

public class MapManager {
    private final String playerName;
    private int playerScore = 0;

    private final int tileSize = 64;
    private final int columns = 15;
    private final int rows = 10;
    private final boolean[][] crushedWheatMap;

    private final double mapWidth = columns * tileSize;
    private final double mapHeight = rows * tileSize;

    public MapManager(String playerName) {
        this.playerName = playerName;
        this.crushedWheatMap = new boolean[columns][rows];
    }

    /**
     * Processes keyboard input to move the player.
     * Returns true if the state changed and the screen needs to be re-rendered.
     */
    public boolean handleKeyPress(KeyCode key, MapCharacter mapCharacter) {
        boolean moved = false;

        if (key == KeyCode.W || key == KeyCode.UP) {
            mapCharacter.move(0, -mapCharacter.getSpeed(), mapWidth, mapHeight);
            moved = true;
        } else if (key == KeyCode.S || key == KeyCode.DOWN) {
            mapCharacter.move(0, mapCharacter.getSpeed(), mapWidth, mapHeight);
            moved = true;
        } else if (key == KeyCode.A || key == KeyCode.LEFT) {
            mapCharacter.move(-mapCharacter.getSpeed(),0, mapWidth, mapHeight);
             moved = true;
        } else if (key == KeyCode.D || key == KeyCode.RIGHT) {
            mapCharacter.move(mapCharacter.getSpeed(),0, mapWidth, mapHeight);
            moved = true;
        }

        if (moved) {
            crushWheatAtPosition(mapCharacter.getPositionX() + mapCharacter.getRenderedWidth() / 2,
                    mapCharacter.getPositionY() + mapCharacter.getRenderedHeight() / 2);
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

    public String getPlayerName() { return this.playerName; }
    public int getPlayerScore() { return this.playerScore; }

    public int getColumns() { return this.columns; }
    public int getRows() { return this.rows; }
    public int getTileSize() { return this.tileSize; }

    public boolean isWheatCrushed(int x, int y) {
        return this.crushedWheatMap[x][y];
    }
}
