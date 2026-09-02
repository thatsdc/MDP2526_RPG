package it.unicam.cs.mpgc.rpg129072;

import it.unicam.cs.mpgc.rpg129072.screens.MenuScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MenuScreen menu = new MenuScreen(stage);
        Scene scene = new Scene(menu);
        stage.setScene(scene);
        stage.setTitle("Game");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.show();
    }
}
