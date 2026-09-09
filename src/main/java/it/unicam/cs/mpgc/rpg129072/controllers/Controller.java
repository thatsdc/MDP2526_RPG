package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

abstract class Controller {
    protected final Stage stage;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage(){
        return stage;
    }
}
