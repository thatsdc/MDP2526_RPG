package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

public class GameOverController extends Controller{

    private final String playerName;
    private final int playerScore;
    private final Runnable onRestartGame;
    private final Runnable onMainMenu;

    public GameOverController(Stage stage, String playerName, int playerScore, Runnable onRestartGame, Runnable onMainMenu) {
        super(stage);
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.onRestartGame = onRestartGame;
        this.onMainMenu = onMainMenu;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
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
