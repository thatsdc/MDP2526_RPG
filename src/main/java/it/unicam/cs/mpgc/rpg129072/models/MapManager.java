package it.unicam.cs.mpgc.rpg129072.models;

import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;
import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapCharacter;
import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapEnemy;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private final String playerName;
    private int playerScore = 0;

    private final int tileSize = 64;
    private final int columns = 15;
    private final int rows = 10;
    private final boolean[][] crushedWheatMap;

    private final double mapWidth = columns * tileSize;
    private final double mapHeight = rows * tileSize;

    private final MapCharacter player;
    private final List<MapEnemy> enemies;

    public MapManager(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;

        this.crushedWheatMap = new boolean[columns][rows];
        this.player = new MapCharacter(
                "/characters/map/elf-lord.png",
            100, 80,
        16, 16,
        4, 2.5
        );

        this.enemies = new ArrayList<>(
            List.of(
            new MapEnemy(
                EnemyType.MERFOLK,
            200, 80,
        16, 16,
        4, 2.5
            )
          )
        );
    }

    /**
     * Registers a new enemy in the map manager.
     */
    public void addEnemy(MapEnemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Evaluates which enemies are standing on crushed wheat tiles.
     * Only these enemies should be rendered by the MapScreen.
     */
    public MapEnemy getVisibleEnemy() {
        for (MapEnemy enemy : this.enemies) {
            int gridX = (int) ((enemy.getPositionX() + enemy.getRenderedWidth() / 2) / this.tileSize);
            int gridY = (int) ((enemy.getPositionY() + enemy.getRenderedHeight() / 2) / this.tileSize);

            // Ensure coordinates are within map boundaries
            if (gridX >= 0 && gridX < this.columns && gridY >= 0 && gridY < this.rows) {
                if (this.crushedWheatMap[gridX][gridY]) {
                    return enemy;
                }
            }
        }

        return null;
    }

    /**
     * Processes keyboard input to move the player.
     * Returns true if the state changed and the screen needs to be re-rendered.
     */
    public boolean handleKeyPress(KeyCode key) {
        boolean moved = false;

        if (key == KeyCode.W || key == KeyCode.UP) {
            player.move(0, -player.getSpeed(), mapWidth, mapHeight);
            moved = true;
        } else if (key == KeyCode.S || key == KeyCode.DOWN) {
            player.move(0, player.getSpeed(), mapWidth, mapHeight);
            moved = true;
        } else if (key == KeyCode.A || key == KeyCode.LEFT) {
            player.move(-player.getSpeed(),0, mapWidth, mapHeight);
             moved = true;
        } else if (key == KeyCode.D || key == KeyCode.RIGHT) {
            player.move(player.getSpeed(),0, mapWidth, mapHeight);
            moved = true;
        }

        if (moved) {
            crushWheatAtPosition(player.getPositionX() + player.getRenderedWidth() / 2,
                player.getPositionY() + player.getRenderedHeight() / 2);
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

    public MapCharacter getPlayer(){
        return player;
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
