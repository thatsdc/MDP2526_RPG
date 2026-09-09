package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;

import java.util.function.Consumer;

public class SetNameController extends Controller {

    Runnable onBackClick;
    Consumer<String> onSubmitName;

    public SetNameController(Stage stage, Runnable onBackClick, Consumer<String> onSubmitName) {
        this.onBackClick = onBackClick;
        this.onSubmitName = onSubmitName;
        super(stage);
    }

    public void submitName(String name){
        onSubmitName.accept(name);
    }

    public void goBack(){
        onBackClick.run();
    }
}
