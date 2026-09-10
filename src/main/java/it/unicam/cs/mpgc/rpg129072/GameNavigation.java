package it.unicam.cs.mpgc.rpg129072;

import it.unicam.cs.mpgc.rpg129072.controllers.IntroController;
import it.unicam.cs.mpgc.rpg129072.controllers.MapController;
import it.unicam.cs.mpgc.rpg129072.controllers.MenuController;
import it.unicam.cs.mpgc.rpg129072.controllers.SetNameController;
import it.unicam.cs.mpgc.rpg129072.models.MapManager;
import it.unicam.cs.mpgc.rpg129072.screens.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameNavigation extends Application {

    private Stage mainStage;

    private Scene menuScene;
    private Scene introScene;
    private Scene setNameScene;
    private Scene mapScene;


    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        navigateTo(Route.MENU);
        mainStage.setTitle("Game Title");
        mainStage.setWidth(1280);
        mainStage.setHeight(720);
        mainStage.setResizable(false);
        mainStage.show();
    }


    private void navigateTo(Route route, Map<String, String> payload){
        switch (route) {
            case MENU:
                if (menuScene == null){
                    MenuController menuController = new MenuController(
                            mainStage,
                            () -> navigateTo(Route.SET_NAME),
                            () -> navigateTo(Route.LEADERBOARD),
                            () -> navigateTo(Route.SETTINGS)
                    );
                    menuScene = new MenuScreen(menuController).generateScene();
                }
                mainStage.setScene(menuScene);
                break;

            case SET_NAME:
                if (setNameScene == null) {
                    SetNameController setNameController = new SetNameController(
                        mainStage,
                        () -> navigateTo(Route.MENU),
                        (playerName) -> navigateTo(Route.INTRO, Map.of("playerName", playerName))
                    );
                    setNameScene = new SetNameScreen(setNameController).generateScene();
                }
                mainStage.setScene(setNameScene);
                break;

            case INTRO:
                if (introScene == null){
                    String playerName = payload.get("playerName");

                    IntroController introController = new IntroController(
                        mainStage,
                        () -> navigateTo(Route.GAME_MAP, Map.of("playerName", playerName))
                        );
                    introScene = new IntroScreen(introController).generateScene();
                }
                mainStage.setScene(introScene);
                break;

            case GAME_MAP:
                if (mapScene == null){
                    String playerName = payload.get("playerName");
                    MapController mapController = new MapController(mainStage);
                    MapManager mapManager = new MapManager(playerName);
                    mapScene = new MapScreen(mapController, mapManager).generateScene();
                }
                mainStage.setScene(mapScene);
                break;

        };
    }

    private void navigateTo(Route route){
        navigateTo(route, Collections.emptyMap());
    }
}
