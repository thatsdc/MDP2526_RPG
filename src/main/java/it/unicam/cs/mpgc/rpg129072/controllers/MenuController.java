package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

public class MenuController extends Controller {
    Runnable onStartClick;
    Runnable onSettingsClick;

    public MenuController(Stage stage,
                          Runnable onStartClick,
                          Runnable onSettingsClick){
        this.onStartClick = onStartClick;
        this.onSettingsClick = onSettingsClick;
        super(stage);
    }

    public void startGame(){
        onStartClick.run();
    }

    public void openSettings(){
        onSettingsClick.run();
    }


    public void quitGame(){
        stage.close();
    }
}
