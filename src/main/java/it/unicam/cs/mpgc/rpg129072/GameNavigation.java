package it.unicam.cs.mpgc.rpg129072;

import it.unicam.cs.mpgc.rpg129072.controllers.*;
import it.unicam.cs.mpgc.rpg129072.enums.EnemyType;
import it.unicam.cs.mpgc.rpg129072.enums.Route;
import it.unicam.cs.mpgc.rpg129072.managers.CombatManager;
import it.unicam.cs.mpgc.rpg129072.managers.MapManager;
import it.unicam.cs.mpgc.rpg129072.managers.SettingsManager;
import it.unicam.cs.mpgc.rpg129072.screens.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class GameNavigation extends Application {

    private Stage mainStage;
    private SettingsManager settingsManager;

    private Scene menuScene;
    private Scene introScene;
    private Scene setNameScene;
    private Scene mapScene;
    private Scene combatScene;
    private Scene gameOverScene;
    private Scene settingsScene;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        settingsManager = new SettingsManager(stage);
        mainStage.setTitle("The Village Defender");
        mainStage.setResizable(false);
        navigateTo(Route.MENU);
        mainStage.show();
    }

    private void navigateTo(Route route){
        navigateTo(route, Collections.emptyMap());
    }

    private void navigateTo(Route route, Map<String, String> payload){
        navigateTo(route, payload, false);
    }

    private void navigateTo(Route route, Map<String, String> payload, boolean reset){
        switch (route) {
            case MENU:
                if (menuScene == null || reset){
                    MenuController menuController = new MenuController(
                            mainStage,
                            () -> navigateTo(Route.SET_NAME, Map.of(), true),
                            () -> navigateTo(Route.SETTINGS)
                    );
                    menuScene = new MenuScreen(menuController).generateScene();
                }
                mainStage.setScene(menuScene);
                break;

            case SET_NAME:
                if (setNameScene == null || reset) {
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
                if (introScene == null || reset){
                    String playerName = payload.get("playerName");

                    IntroController introController = new IntroController(
                        mainStage,
                        () -> navigateTo(Route.MAP,
                                Map.of("playerName", playerName, "playerScore", "0"),
                                true
                                )
                        );
                    introScene = new IntroScreen(introController).generateScene();
                }
                mainStage.setScene(introScene);
                break;

            case MAP:
                if (mapScene == null || reset){
                    String playerName = payload.get("playerName");
                    int playerScore = Integer.parseInt(payload.get("playerScore"));
                    String playerPosStr = payload.get("playerPos");
                    String[] playerPos = null;

                    if (playerPosStr != null){
                        playerPos = playerPosStr.split("_");
                    }

                    MapController mapController = new MapController(
                        mainStage,
                        (args) -> {
                            navigateTo(Route.COMBAT, args, true);
                        }
                    );

                    MapManager mapManager;
                    if(playerPos != null){
                        mapManager = new MapManager(playerName, playerScore,
                            Double.parseDouble(playerPos[0]),
                            Double.parseDouble(playerPos[1]));
                    }else{
                        mapManager = new MapManager(playerName, playerScore);
                    }

                    mapScene = new MapScreen(mapController, mapManager).generateScene();
                }
                mainStage.setScene(mapScene);
                break;

            case COMBAT:
                if (combatScene == null || reset){
                    String playerName = payload.get("playerName");
                    int playerScore = Integer.parseInt(payload.get("playerScore"));
                    EnemyType enemyType = EnemyType.valueOf(payload.get("enemyType"));
                    String playerPosStr = payload.get("playerPos");

                    CombatController combatController = new CombatController(
                            mainStage,
                            (args) -> {
                                if(playerPosStr != null){
                                    args.put("playerPos", playerPosStr);
                                }

                                navigateTo(Route.MAP, args, true);
                                },
                            (args) -> navigateTo(Route.GAME_OVER, args)
                            );
                    CombatManager combatManager = new CombatManager(playerName, playerScore, enemyType);
                    String[] savedRes = settingsManager.getCurrentResolution().split("x");
                    int currWidth = Integer.parseInt(savedRes[0]);
                    int currHeight = Integer.parseInt(savedRes[1]);
                    combatScene = new CombatScreen(combatController, combatManager, currWidth, currHeight).generateScene();
                }
                mainStage.setScene(combatScene);
                break;

            case GAME_OVER:
                if(gameOverScene == null || reset){
                    String playerName = payload.get("playerName");
                    int playerScore = Integer.parseInt(payload.get("playerScore"));

                    GameOverController gameOverController = new GameOverController(
                            mainStage, playerName, playerScore,
                            () -> navigateTo(Route.MAP, payload, true),
                            () -> navigateTo(Route.MENU)
                            );
                    gameOverScene = new GameOverScreen(gameOverController).generateScene();
                }
                mainStage.setScene(gameOverScene);
                break;

            case SETTINGS:
                if(settingsScene == null || reset){
                    SettingsController settingsController = new SettingsController(mainStage, () -> navigateTo(Route.MENU));
                    settingsScene = new SettingsScreen(settingsController, settingsManager).generateScene();
                }
                mainStage.setScene(settingsScene);
                break;
        };
    }

}
