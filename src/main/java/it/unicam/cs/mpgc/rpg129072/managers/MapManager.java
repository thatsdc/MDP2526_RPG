package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapCharacter;
import it.unicam.cs.mpgc.rpg129072.components.MapCharacter.MapEnemy;
import it.unicam.cs.mpgc.rpg129072.enums.Direction;
import it.unicam.cs.mpgc.rpg129072.records.Position;
import it.unicam.cs.mpgc.rpg129072.session.GameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MapManager implements MapState {

    private static final int TILE_SIZE = 64;
    private static final int COLUMNS = 15;
    private static final int ROWS = 10;
    private static final double MAP_WIDTH = COLUMNS * TILE_SIZE;
    private static final double MAP_HEIGHT = ROWS * TILE_SIZE;

    private static final double FRAME_WIDTH = 16;
    private static final double FRAME_HEIGHT = 16;
    private static final int TOTAL_FRAMES = 4;
    private static final double SCALE = 2.5;

    private static final int ENEMY_COUNT = 10;
    private static final Position DEFAULT_START = new Position(100, 80);
    private static final String PLAYER_SPRITE = "/characters/map/lord.png";

    private final GameSession session;
    private final EnemyRegistry enemyRegistry;
    private final Random random = new Random();

    private final boolean[][] crushedWheatMap = new boolean[COLUMNS][ROWS];
    private final MapCharacter player;
    private final List<MapEnemy> enemies = new ArrayList<>();

    private MapEnemy visibleEnemy;
    private boolean freezePlayer = false;

    public MapManager(GameSession session, EnemyRegistry enemyRegistry) {
        this.session = Objects.requireNonNull(session, "session");
        this.enemyRegistry = Objects.requireNonNull(enemyRegistry, "enemyRegistry");

        Position start = session.getPlayerPosition().orElse(DEFAULT_START);
        this.player = new MapCharacter(PLAYER_SPRITE,
                start.x(), start.y(), FRAME_WIDTH, FRAME_HEIGHT, TOTAL_FRAMES, SCALE);

        int[] playerGrid = getCenterGrid(player);
        crushWheatAtPosition(playerGrid[0], playerGrid[1]);

        generateRandomEnemies(ENEMY_COUNT);
    }

    /**
     * Generates a specified number of enemies at random positions on the map,
     * never on the tile where the player stands.
     */
    public void generateRandomEnemies(int amount) {
        double renderedWidth = FRAME_WIDTH * SCALE;
        double renderedHeight = FRAME_HEIGHT * SCALE;

        int[] playerGrid = getCenterGrid(player);
        enemies.clear();

        for (int i = 0; i < amount; i++) {
            double randomX, randomY;
            int[] enemyGrid;

            // Retry until the enemy is not on the player's tile
            do {
                randomX = random.nextDouble() * (MAP_WIDTH - renderedWidth);
                randomY = random.nextDouble() * (MAP_HEIGHT - renderedHeight);
                enemyGrid = getPositionGrid(randomX + renderedWidth / 2, randomY + renderedHeight / 2);
            } while (enemyGrid[0] == playerGrid[0] && enemyGrid[1] == playerGrid[1]);

            String enemyId = enemyRegistry.random(random).id();
            enemies.add(new MapEnemy(enemyId, randomX, randomY,
                    FRAME_WIDTH, FRAME_HEIGHT, TOTAL_FRAMES, SCALE));
        }
    }

    /**
     * Moves the player one step, crushes the wheat below it and checks
     * whether an enemy has been uncovered.
     * @return true if the player moved and the screen needs to be re-rendered
     */
    public boolean movePlayer(Direction direction) {
        if (freezePlayer) return false;

        double step = player.getSpeed();
        player.move(direction.dx() * step, direction.dy() * step, MAP_WIDTH, MAP_HEIGHT);

        int[] playerGrid = getCenterGrid(player);
        crushWheatAtPosition(playerGrid[0], playerGrid[1]);

        visibleEnemy = findEnemyOnCrushedWheat();
        if (visibleEnemy != null) {
            freezePlayer = true;
        }
        return true;
    }

    /**
     * Stores the player position and the uncovered enemy in the session,
     * so that the rest of the screens can read them.
     */
    public void startEncounter() {
        if (visibleEnemy == null) {
            throw new IllegalStateException("No enemy has been uncovered");
        }
        session.savePlayerPosition(player.getPositionX(), player.getPositionY());
        session.startEncounter(enemyRegistry.get(visibleEnemy.getEnemyId()));
    }

    /**
     * Obtains a specific position and returns the grid coords containing it.
     */
    public int[] getPositionGrid(double posX, double posY) {
        return new int[]{(int) (posX / TILE_SIZE), (int) (posY / TILE_SIZE)};
    }

    private void crushWheatAtPosition(int gridX, int gridY) {
        if (isInsideMap(gridX, gridY)) {
            crushedWheatMap[gridX][gridY] = true;
        }
    }

    private MapEnemy findEnemyOnCrushedWheat() {
        for (MapEnemy enemy : enemies) {
            int[] enemyGrid = getCenterGrid(enemy);
            if (isWheatCrushed(enemyGrid[0], enemyGrid[1])) {
                return enemy;
            }
        }
        return null;
    }

    private int[] getCenterGrid(MapCharacter character) {
        // Grid coords of the tile under the character's center
        return getPositionGrid(
                character.getPositionX() + character.getRenderedWidth() / 2,
                character.getPositionY() + character.getRenderedHeight() / 2);
    }

    private boolean isInsideMap(int gridX, int gridY) {
        return gridX >= 0 && gridX < COLUMNS && gridY >= 0 && gridY < ROWS;
    }

    // --- MapState ---

    /**
     * Returns the enemy uncovered by the player, or null if none has been found yet.
     */
    @Override
    public MapEnemy getVisibleEnemy() {
        return visibleEnemy;
    }

    @Override
    public boolean isWheatCrushed(int x, int y) {
        return isInsideMap(x, y) && crushedWheatMap[x][y];
    }

    @Override public MapCharacter getPlayer() { return player; }
    @Override public String getPlayerName() { return session.getPlayerName(); }
    @Override public int getPlayerScore() { return session.getScore(); }
    @Override public int getColumns() { return COLUMNS; }
    @Override public int getRows() { return ROWS; }
    @Override public int getTileSize() { return TILE_SIZE; }

    public boolean getFreezePlayer() {
        return freezePlayer;
    }
}