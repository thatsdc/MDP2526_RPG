package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

public class SettingsController extends Controller{

    private final Runnable onBackToMenu;

    public SettingsController(Stage stage, Runnable onBackToMenu) {
        this.onBackToMenu = onBackToMenu;
        super(stage);
    }

    public void backToMenu(){
        onBackToMenu.run();
    }
}
