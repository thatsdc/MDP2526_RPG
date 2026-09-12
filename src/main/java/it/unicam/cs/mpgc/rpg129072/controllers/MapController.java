package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Consumer;

public class MapController extends Controller {
    Consumer<Map<String, String>> onStartCombat;

    public MapController(Stage stage, Consumer<Map<String, String>> onStartCombat) {
        super(stage);
        this.onStartCombat = onStartCombat;
    }

    public void startCombat(Map<String, String> payload){
        onStartCombat.accept(payload);
    }
}