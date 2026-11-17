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

    public void submitName(String name) {
        onSubmitName.accept(name);
    }

    public UnaryOperator getLengthFilter(){
        // Restrict input to a maximum of 8 characters
        UnaryOperator<TextFormatter.Change> lengthFilter = change -> {
            if (change.getControlNewText().length() <= 8) {
                return change;
            }
            return null;
        };
    }

    public void goBack(){
        onBackClick.run();
    }
}
