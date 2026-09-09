package it.unicam.cs.mpgc.rpg129072.screens;

import it.unicam.cs.mpgc.rpg129072.controllers.MapController;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class MapScreen extends Canvas implements Renderable {

    public MapScreen(MapController mapController) {
    }

    public Scene generateScene(){
        StackPane root = new StackPane(this);
        return new Scene(root);
    }
}
