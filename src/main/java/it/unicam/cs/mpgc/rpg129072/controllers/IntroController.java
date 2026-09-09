package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

public class IntroController extends Controller {

    Runnable onSkipIntro;

    public IntroController(Stage stage, Runnable onSkipIntro) {
        super(stage);
        this.onSkipIntro = onSkipIntro;
    }

    public void finishIntro(){
        onSkipIntro.run();
    }
}
