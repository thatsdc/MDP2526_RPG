package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.persistence.JSONPersistence;
import it.unicam.cs.mpgc.rpg129072.persistence.models.Settings;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager {
    private final JSONPersistence<Settings> settingsPersistence = new JSONPersistence<>("settings.json", Settings.class);
    private Settings settings;
    private final Stage stage;

    static private final List<String> AVAILABLE_RESOLUTIONS = new ArrayList<>(List.of("1280x720", "1920x1080"));

    public SettingsManager(Stage stage) {
        Settings savedSettings = settingsPersistence.load();
        this.stage = stage;

        if(savedSettings != null) {
            settings = savedSettings;
        }else{
            createSettingsFile();
        }

        initSettings();
    }


    private void initSettings(){
        setStageResolution(settings.getSavedResolution());
    }

    private void createSettingsFile(){
        settings = new Settings();
        settingsPersistence.save(settings);
    }

    public void setScreenResolution(String resolution){
        if(AVAILABLE_RESOLUTIONS.contains(resolution)){
            settings.setSavedResolution(resolution);
            settingsPersistence.save(settings);

            setStageResolution(
                resolution
            );
        }
    }

    private void setStageResolution(String resolution){
        String[] result = resolution.split("x");

        stage.setWidth(Integer.parseInt(result[0]));
        stage.setHeight(Integer.parseInt(result[1]));
    }

    public List<String> getAvailableResolutions(){
        return AVAILABLE_RESOLUTIONS;
    }

    public String getCurrentResolution(){
        return settings.getSavedResolution();
    }
}
