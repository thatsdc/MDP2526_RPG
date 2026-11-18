package it.unicam.cs.mpgc.rpg129072.controllers;

import it.unicam.cs.mpgc.rpg129072.session.GameSession;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.function.Consumer;

public class SetNameController extends Controller {

    private final Runnable onBackClick;
    private final Runnable onSubmitName;
    private final GameSession gameSession;

    public SetNameController(Stage stage, GameSession gameSession, Runnable onBackClick, Runnable onSubmitName) {
        super(stage);
        this.gameSession = gameSession;
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
