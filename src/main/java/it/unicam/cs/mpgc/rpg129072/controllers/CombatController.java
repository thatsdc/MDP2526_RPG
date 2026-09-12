package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Consumer;

public class CombatController {

    Consumer<Map<String, String>> onCombatWin;
    Consumer<Map<String, String>> onCombatDefeat;

    public CombatController(Stage stage,
                            Consumer<Map<String, String>> onCombatWin,
                            Consumer<Map<String, String>> onCombatDefeat) {
        this.onCombatWin = onCombatWin;
        this.onCombatDefeat = onCombatDefeat;
    }

    public void combatWin(Map<String, String> payload){
        onCombatWin.accept(payload);
    }

    public void combatDefeat(Map<String, String> payload){
        onCombatDefeat.accept(payload);
    }
}
