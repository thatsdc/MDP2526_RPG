package it.unicam.cs.mpgc.rpg129072;

import it.unicam.cs.mpgc.rpg129072.controllers.*;
import it.unicam.cs.mpgc.rpg129072.enums.Route;
import it.unicam.cs.mpgc.rpg129072.managers.CombatManager;
import it.unicam.cs.mpgc.rpg129072.managers.EnemyRegistry;
import it.unicam.cs.mpgc.rpg129072.managers.MapManager;
import it.unicam.cs.mpgc.rpg129072.managers.SettingsManager;
import it.unicam.cs.mpgc.rpg129072.screens.*;
import it.unicam.cs.mpgc.rpg129072.session.GameSession;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameNavigation extends Application {

    private Stage mainStage;
    private SettingsManager settingsManager;
    private EnemyRegistry enemyRegistry;
    private final GameSession session = new GameSession();

    private Scene menuScene;
    private Scene introScene;
    private Scene setNameScene;
    private Scene mapScene;
    private Scene combatScene;
    private Scene gameOverScene;
    private Scene settingsScene;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        settingsManager = new SettingsManager(stage);
        enemyRegistry = new EnemyRegistry("/data/enemies.json");

        mainStage.setTitle("The Village Defender");
        mainStage.setResizable(false);
        navigateTo(Route.MENU);
        mainStage.show();
    }

    private void navigateTo(Route route) {
        navigateTo(route, false);
    }

    private void navigateTo(Route route, boolean reset) {
        String[] res = settingsManager.getCurrentResolution().split("x");
        int res_x = Integer.parseInt(res[0]);
        int res_y = Integer.parseInt(res[1]);

        switch (route) {
            case MENU -> {
                if (menuScene == null || reset) {
                    MenuController controller = new MenuController(
                            mainStage,
                            () -> navigateTo(Route.SET_NAME, true),
                            () -> navigateTo(Route.SETTINGS)
                    );
                    menuScene = new MenuScreen(controller, res_x, res_y).generateScene();
                }
                mainStage.setScene(menuScene);
            }

            case SET_NAME -> {
                if (setNameScene == null || reset) {
                    SetNameController controller = new SetNameController(
                            mainStage,
                            session,
                            () -> navigateTo(Route.MENU),
                            () -> navigateTo(Route.INTRO, true)
                    );
                    setNameScene = new SetNameScreen(controller, res_x, res_y).generateScene();
                }
                mainStage.setScene(setNameScene);
            }

            case INTRO -> {
                if (introScene == null || reset) {
                    IntroController controller = new IntroController(
                            mainStage,
                            () -> navigateTo(Route.MAP, true)
                    );
                    introScene = new IntroScreen(controller, res_x, res_y).generateScene();
                }
                mainStage.setScene(introScene);
            }

            case MAP -> {
                if (mapScene == null || reset) {
                    MapManager mapManager = new MapManager(session, enemyRegistry);
                    MapController controller = new MapController(
                            mainStage,
                            mapManager,
                            () -> navigateTo(Route.COMBAT, true)
                    );
                    mapScene = new MapScreen(controller, mapManager).generateScene();
                }
                mainStage.setScene(mapScene);
            }

            case COMBAT -> {
                if (combatScene == null || reset) {
                    CombatManager combatManager = new CombatManager(session);
                    CombatController controller = new CombatController(
                            mainStage,
                            combatManager,
                            session,
                            () -> navigateTo(Route.MAP, true),
                            () -> navigateTo(Route.GAME_OVER, true)
                    );

                    int width = Integer.parseInt(res[0]);
                    int height = Integer.parseInt(res[1]);

                    combatScene = new CombatScreen(controller, combatManager, width, height).generateScene();
                }
                mainStage.setScene(combatScene);
            }

            case GAME_OVER -> {
                if (gameOverScene == null || reset) {
                    GameOverController controller = new GameOverController(
                            mainStage,
                            session,
                            () -> navigateTo(Route.MAP, true),
                            () -> navigateTo(Route.MENU)
                    );
                    gameOverScene = new GameOverScreen(controller).generateScene();
                }
                mainStage.setScene(gameOverScene);
            }

            case SETTINGS -> {
                if (settingsScene == null || reset) {
                    SettingsController controller = new SettingsController(
                            mainStage,
                            () -> navigateTo(Route.MENU)
                    );
                    settingsScene = new SettingsScreen(controller, settingsManager).generateScene();
                }
                mainStage.setScene(settingsScene);
            }
        }
    }
}