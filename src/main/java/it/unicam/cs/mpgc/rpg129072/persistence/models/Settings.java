package it.unicam.cs.mpgc.rpg129072.persistence.models;

public class Settings {
    private String savedResolution;

    public Settings() {
        this("1280x720");
    }

    public Settings(String savedResolution) {
        setSavedResolution(savedResolution);
    }

    public void setSavedResolution(String savedResolution) {
        this.savedResolution = savedResolution;
    }

    public String getSavedResolution() {
        return savedResolution;
    }
}

