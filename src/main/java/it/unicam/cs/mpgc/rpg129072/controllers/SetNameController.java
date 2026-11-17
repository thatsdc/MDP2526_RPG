package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.function.Consumer;

public class SetNameController extends Controller {

    private final Runnable onBackClick;
    private final Consumer<String> onSubmitName;

    public SetNameController(Stage stage, Runnable onBackClick, Consumer<String> onSubmitName) {
        super(stage);
        this.onBackClick = onBackClick;
        this.onSubmitName = onSubmitName;
    }

    public void submitName(String name) {
        onSubmitName.accept(name);
    }

    public UnaryOperator<TextFormatter.Change> getLengthFilter() {
        return change -> {
            if (change.getControlNewText().length() <= 8) {
                return change;
            }
            return null;
        };
    }

    public void goBack() {
        onBackClick.run();
    }
}
