package it.unicam.cs.mpgc.rpg129072.controllers;

import it.unicam.cs.mpgc.rpg129072.managers.SettingsManager;
import it.unicam.cs.mpgc.rpg129072.persistence.models.Resolution;
import javafx.stage.Stage;

import java.util.List;

public class SettingsController extends Controller {

    private final SettingsManager settingsManager;
    private final Runnable onBackToMenu;

    public SettingsController(Stage stage, SettingsManager settingsManager, Runnable onBackToMenu) {
        super(stage);
        this.settingsManager = settingsManager;
        this.onBackToMenu = onBackToMenu;
    }

    public List<Resolution> getAvailableResolutions() {
        return settingsManager.getAvailableResolutions();
    }

    public Resolution getCurrentResolution() {
        return settingsManager.getResolution();
    }

    public void applyResolution(Resolution resolution) {
        if (resolution == null) return;
        settingsManager.setResolution(resolution);
        resizeStage(getStage(), resolution);
    }

    public void backToMenu() {
        onBackToMenu.run();
    }

    // Also used at startup to apply the saved resolution
    public static void resizeStage(Stage stage, Resolution resolution) {
        stage.setWidth(resolution.getWidth());
        stage.setHeight(resolution.getHeight());
        stage.centerOnScreen();
    }
}