package it.unicam.cs.mpgc.rpg129072.controllers;

import it.unicam.cs.mpgc.rpg129072.session.GameSession;
import javafx.stage.Stage;

public class GameOverController extends Controller{
    private final Runnable onRestartGame;
    private final Runnable onMainMenu;
    private final GameSession gameSession;

    public GameOverController(Stage stage, GameSession gameSession, Runnable onRestartGame, Runnable onMainMenu) {
        super(stage);
        this.gameSession = gameSession;
        this.onRestartGame = onRestartGame;
        this.onMainMenu = onMainMenu;
    }

    public void restartGame(){
        onRestartGame.run();
    }

    public void mainMenu(){
        onMainMenu.run();
    }

    public void quitGame(){
        stage.close();
    }

}
