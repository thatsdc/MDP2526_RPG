package it.unicam.cs.mpgc.rpg129072.models;

import java.util.ArrayList;
import java.util.List;

public class ScreenManager {
    static private int screenWidth = 1280;
    static private int screenHeight = 720;

    static private final List<String> AVAILABLE_RESOLUTIONS = new ArrayList<String>(List.of("1920x1080", "1280x720"));

    public static void setScreenResolution(String resolution){
        if(AVAILABLE_RESOLUTIONS.contains(resolution)){
            String[] result = resolution.split("x");
        }
    }

    public static List<String> getAvailableResolutions(){
        return AVAILABLE_RESOLUTIONS;
    }
}
