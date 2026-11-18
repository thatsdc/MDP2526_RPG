package it.unicam.cs.mpgc.rpg129072.controllers;

import javafx.application.Platform;
import javafx.stage.Stage;
import java.util.Objects;

public class MenuController extends Controller {

    private final Runnable onStartGame;
    private final Runnable onOpenSettings;

    public MenuController(Stage stage, Runnable onStartGame, Runnable onOpenSettings) {
        super(stage);
        this.onStartGame = Objects.requireNonNull(onStartGame, "onStartGame");
        this.onOpenSettings = Objects.requireNonNull(onOpenSettings, "onOpenSettings");
    }

    public void startGame() {
        onStartGame.run();
    }

    public void openSettings() {
        onOpenSettings.run();
    }

    public void quitGame() {
        Platform.exit();
    }
}