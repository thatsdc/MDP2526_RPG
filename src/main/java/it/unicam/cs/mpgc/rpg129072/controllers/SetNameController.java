package it.unicam.cs.mpgc.rpg129072.controllers;

import it.unicam.cs.mpgc.rpg129072.session.GameSession;
import javafx.stage.Stage;

import java.util.Objects;

public class SetNameController extends Controller {

    private static final int MAX_NAME_LENGTH = 8;

    private final GameSession gameSession;
    private final Runnable onBackClick;
    private final Runnable onNameConfirmed;

    public SetNameController(Stage stage, GameSession gameSession,
                             Runnable onBackClick, Runnable onNameConfirmed) {
        super(stage);
        this.gameSession = Objects.requireNonNull(gameSession, "gameSession");
        this.onBackClick = Objects.requireNonNull(onBackClick, "onBackClick");
        this.onNameConfirmed = Objects.requireNonNull(onNameConfirmed, "onNameConfirmed");
    }

    public int getMaxNameLength() {
        return MAX_NAME_LENGTH;
    }

    /**
     * Validates the name and, if valid, starts a new game.
     * @return true if the name was accepted, false otherwise
     */
    public boolean submitName(String rawName) {
        if (rawName == null) return false;

        String name = rawName.strip();
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH) return false;

        gameSession.startNewGame(name);
        onNameConfirmed.run();
        return true;
    }

    public void goBack() {
        onBackClick.run();
    }
}