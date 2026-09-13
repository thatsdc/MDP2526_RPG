package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;
import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapCharacter;
import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapEnemy;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapManager {
    private final String playerName;
    private int playerScore;

    private boolean freezePlayer = false;

    private final int tileSize = 64;
    private final int columns = 15;
    private final int rows = 10;
    private final boolean[][] crushedWheatMap;

    private final double mapWidth = columns * tileSize;
    private final double mapHeight = rows * tileSize;

    private final MapCharacter player;
    private final List<MapEnemy> enemies;

    public MapManager(String playerName, int playerScore){
        this(playerName, playerScore, 100, 80);
    }

    public MapManager(String playerName, int playerScore, double playerPosX, double playerPosY) {
        this.playerName = playerName;
        this.playerScore = playerScore;

        this.crushedWheatMap = new boolean[columns][rows];
        this.player = new MapCharacter(
                "/characters/map/elf-lord.png",
                playerPosX, playerPosY,
        16, 16,
        4, 2.5
        );
        this.crushWheatAtPosition(playerPosX, playerPosY);

        this.enemies = new ArrayList<>();
        this.generateRandomEnemies(10);
    }

    /**
     * Generates a specified number of enemies at random positions on the map.
     */
    public void generateRandomEnemies(int amount) {
        Random random = new Random();

        // Calculate the rendered size to prevent enemies from spawning outside the map bounds
        double frameWidth = 16;
        double frameHeight = 16;
        double scale = 2.5;
        double renderedWidth = frameWidth * scale;
        double renderedHeight = frameHeight * scale;

        this.enemies.clear();

        for (int i = 0; i < amount; i++) {
            int[] enemyGridCoords = null, playerGridCoords = null;
            double randomX = 0, randomY = 0;

            while(!(playerGridCoords != null && enemyGridCoords != null && playerGridCoords[0] != enemyGridCoords[0] && playerGridCoords[1] != enemyGridCoords[1])) {
                randomX = random.nextDouble() * (this.mapWidth - renderedWidth);
                randomY = random.nextDouble() * (this.mapHeight - renderedHeight);

                enemyGridCoords = this.getPositionGrid(randomX, randomY);
                playerGridCoords = this.getPositionGrid(player.getPositionX(), player.getPositionY());
            }


            EnemyType[] enemyTypes = EnemyType.values();
            EnemyType randomType = enemyTypes[random.nextInt(enemyTypes.length)];

            MapEnemy newEnemy = new MapEnemy(
                    randomType,
                    randomX,
                    randomY,
                    frameWidth,
                    frameHeight,
                    4,
                    scale
            );

            this.enemies.add(newEnemy);
        }
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
     * * Obtains a specific position and returns the grid coords containing it.
     * **/
    public int[] getPositionGrid(double posX, double posY){
        int gridX = (int) (posX / tileSize);
        int gridY = (int) (posY / tileSize);

        return new int[]{gridX, gridY};
    }

    /**
     * Processes keyboard input to move the player.
     * Returns true if the state changed and the screen needs to be re-rendered.
     */
    public boolean handleKeyPress(KeyCode key) {
        if(freezePlayer) return false;

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
        int[] gridCoords = getPositionGrid(targetX, targetY);
        int gridX = gridCoords[0];
        int gridY = gridCoords[1];

        if (gridX >= 0 && gridX < columns && gridY >= 0 && gridY < rows) {
            if (!this.crushedWheatMap[gridX][gridY]) {
                this.crushedWheatMap[gridX][gridY] = true;
            }
        }
    }

    public MapCharacter getPlayer(){
        return player;
    }
    public String getPlayerName() { return this.playerName; }
    public int getPlayerScore() { return this.playerScore; }

    public boolean getFreezePlayer(){
        return this.freezePlayer;
    }
    public void setFreezePlayer(boolean v){
        this.freezePlayer = v;
    }

    public int getColumns() { return this.columns; }
    public int getRows() { return this.rows; }
    public int getTileSize() { return this.tileSize; }

    public boolean isWheatCrushed(int x, int y) {
        return this.crushedWheatMap[x][y];
    }
}
