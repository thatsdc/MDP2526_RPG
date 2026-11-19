package it.unicam.cs.mpgc.rpg129072.managers;

import it.unicam.cs.mpgc.rpg129072.persistence.JSONPersistence;
import it.unicam.cs.mpgc.rpg129072.persistence.Persistence;
import it.unicam.cs.mpgc.rpg129072.persistence.models.Resolution;
import it.unicam.cs.mpgc.rpg129072.persistence.models.Settings;

import java.util.List;
import java.util.Objects;

public class SettingsManager {
    private static final Resolution DEFAULT_RESOLUTION = Resolution.HD;

    private final Persistence<Settings> settingsPersistence =
            new JSONPersistence<>("settings.json", Settings.class);
    private final Settings settings;

    public SettingsManager() {
        Settings loaded = settingsPersistence.load();
        settings = (loaded != null) ? loaded : new Settings();

        // Missing file or invalid value: reset to default and persist
        if (loaded == null || Resolution.fromString(settings.getSavedResolution()).isEmpty()) {
            settings.setSavedResolution(DEFAULT_RESOLUTION.toString());
            settingsPersistence.save(settings);
        }
    }

    public Resolution getResolution() {
        return Resolution.fromString(settings.getSavedResolution()).orElse(DEFAULT_RESOLUTION);
    }

    public void setResolution(Resolution resolution) {
        Objects.requireNonNull(resolution, "resolution");
        settings.setSavedResolution(resolution.toString());
        settingsPersistence.save(settings);
    }

    public List<Resolution> getAvailableResolutions() {
        return List.of(Resolution.values());
    }
}