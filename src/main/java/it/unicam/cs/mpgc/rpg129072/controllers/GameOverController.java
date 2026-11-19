package it.unicam.cs.mpgc.rpg129072.controllers;

import it.unicam.cs.mpgc.rpg129072.session.GameSession;
import javafx.stage.Stage;

import java.util.Objects;

public class GameOverController extends Controller {

    private final GameSession gameSession;
    private final Runnable onRestartGame;
    private final Runnable onMainMenu;

    public GameOverController(Stage stage, GameSession gameSession,
                              Runnable onRestartGame, Runnable onMainMenu) {
        super(stage);
        this.gameSession = Objects.requireNonNull(gameSession, "gameSession");
        this.onRestartGame = Objects.requireNonNull(onRestartGame, "onRestartGame");
        this.onMainMenu = Objects.requireNonNull(onMainMenu, "onMainMenu");
    }

    public String getPlayerName() {
        return gameSession.getPlayerName();
    }

    public int getPlayerScore() {
        return gameSession.getScore();
    }

    /**
     * Starts a fresh game with the same player name: score, position and encounter are reset.
     */
    public void restartGame() {
        gameSession.startNewGame(gameSession.getPlayerName());
        onRestartGame.run();
    }

    public void mainMenu() {
        onMainMenu.run();
    }
}