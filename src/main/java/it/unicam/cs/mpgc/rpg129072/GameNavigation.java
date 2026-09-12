package it.unicam.cs.mpgc.rpg129072;

import it.unicam.cs.mpgc.rpg129072.controllers.*;
import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;
import it.unicam.cs.mpgc.rpg129072.enums.Route;
import it.unicam.cs.mpgc.rpg129072.models.CombatManager;
import it.unicam.cs.mpgc.rpg129072.models.MapManager;
import it.unicam.cs.mpgc.rpg129072.screens.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class GameNavigation extends Application {

    private Stage mainStage;

    private Scene menuScene;
    private Scene introScene;
    private Scene setNameScene;
    private Scene mapScene;
    private Scene combatScene;
    private Scene gameOverScene;

    private final int SCREEN_WIDTH = 1280;
    private final int SCREEN_HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        navigateTo(Route.MENU);
        mainStage.setTitle("Game Title");
        mainStage.setWidth(SCREEN_WIDTH);
        mainStage.setHeight(SCREEN_HEIGHT);
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
                        () -> navigateTo(Route.MAP,
                                Map.of("playerName", playerName, "playerScore", "0"))
                        );
                    introScene = new IntroScreen(introController).generateScene();
                }
                mainStage.setScene(introScene);
                break;

            case MAP:
                if (mapScene == null){
                    String playerName = payload.get("playerName");
                    int playerScore = Integer.parseInt(payload.get("playerScore"));

                    MapController mapController = new MapController(
                        mainStage,
                        (args) -> navigateTo(Route.COMBAT, args)
                    );
                    MapManager mapManager = new MapManager(playerName, playerScore);
                    mapScene = new MapScreen(mapController, mapManager).generateScene();
                }
                mainStage.setScene(mapScene);
                break;

            case COMBAT:
                if (combatScene == null){
                    String playerName = payload.get("playerName");
                    int playerScore = Integer.parseInt(payload.get("playerScore"));
                    EnemyType enemyType = EnemyType.valueOf(payload.get("enemyType"));

                    CombatController combatController = new CombatController(
                            mainStage,
                            (args) -> navigateTo(Route.MAP, args),
                            (args) -> navigateTo(Route.GAME_OVER, args)
                            );
                    CombatManager combatManager = new CombatManager(playerName, playerScore, enemyType);
                    combatScene = new CombatScreen(combatController, combatManager, SCREEN_WIDTH, SCREEN_HEIGHT).generateScene();
                }
                mainStage.setScene(combatScene);
                break;

            case GAME_OVER:
                if(gameOverScene == null){
                    String playerName = payload.get("playerName");
                    int playerScore = Integer.parseInt(payload.get("playerScore"));

                    GameOverController gameOverController = new GameOverController(
                            mainStage, playerName, playerScore,
                            () -> navigateTo(Route.MAP),
                            () -> navigateTo(Route.MENU)
                            );
                    gameOverScene = new GameOverScreen(gameOverController).generateScene();
                }
                mainStage.setScene(gameOverScene);
                break;
        };
    }

    private void navigateTo(Route route){
        navigateTo(route, Collections.emptyMap());
    }
}
