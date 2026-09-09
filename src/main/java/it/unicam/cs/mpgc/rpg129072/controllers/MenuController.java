package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

public class MenuController extends Controller {
    Runnable onStartClick;
    Runnable onLeaderboardClick;
    Runnable onSettingsClick;

    public MenuController(Stage stage,
                          Runnable onStartClick,
                          Runnable onLeaderboardClick,
                          Runnable onSettingsClick){
        this.onStartClick = onStartClick;
        this.onLeaderboardClick = onLeaderboardClick;
        this.onSettingsClick = onSettingsClick;
        super(stage);
    }

    public void startGame(){
        onStartClick.run();
    }

    public void openSettings(){
        onSettingsClick.run();
    }

    public void openLeaderboard() {
        onLeaderboardClick.run();
    }

    public void quitGame(){
        stage.close();
    }
}
