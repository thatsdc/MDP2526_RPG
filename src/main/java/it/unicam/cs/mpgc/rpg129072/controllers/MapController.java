package it.unicam.cs.mpgc.rpg129072.controllers;

import it.unicam.cs.mpgc.rpg129072.enums.Direction;
import it.unicam.cs.mpgc.rpg129072.managers.MapManager;
import javafx.animation.PauseTransition;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class MapController extends Controller {

    private static final Duration COMBAT_DELAY = Duration.seconds(1);

    private final MapManager mapManager;
    private final Runnable onStartCombat;

    public MapController(Stage stage, MapManager mapManager, Runnable onStartCombat) {
        super(stage);
        this.mapManager = Objects.requireNonNull(mapManager, "mapManager");
        this.onStartCombat = Objects.requireNonNull(onStartCombat, "onStartCombat");
    }

    /**
     * Processes keyboard input to move the player.
     * @return true if the state changed and the screen needs to be re-rendered
     */
    public boolean handleKeyPress(KeyCode key) {
        Direction direction = toDirection(key);
        if (direction == null) return false;

        boolean moved = mapManager.movePlayer(direction);
        if (moved && mapManager.getFreezePlayer()) {
            startCombat();
        }
        return moved;
    }

    private void startCombat() {
        mapManager.startEncounter();

        PauseTransition delay = new PauseTransition(COMBAT_DELAY);
        delay.setOnFinished(event -> onStartCombat.run());
        delay.play();
    }

    private static Direction toDirection(KeyCode key) {
        return switch (key) {
            case W, UP -> Direction.UP;
            case S, DOWN -> Direction.DOWN;
            case A, LEFT -> Direction.LEFT;
            case D, RIGHT -> Direction.RIGHT;
            default -> null;
        };
    }
}