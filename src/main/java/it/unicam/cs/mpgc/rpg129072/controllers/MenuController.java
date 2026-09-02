package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

public class MenuController {

    private final Stage stage;

    public MenuController(Stage stage){
        this.stage = stage;
    }

    public void startGame(){
        System.out.println("Starting the game...");
    }

    public void openSettings(){
        System.out.println("Opening options...");
    }

    public void quitGame(){
        stage.close();
    }

}
