package it.unicam.cs.mpgc.rpg129072.components.Button;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SettingsButton extends Button {
    public SettingsButton(String text, Runnable onClick) {
        super(text);
        this.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        this.setPrefWidth(120);

        String defaultStyle = "-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-width: 2px; -fx-cursor: hand; -fx-padding: 10px;";
        String hoverStyle = "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: white; -fx-border-width: 2px; -fx-cursor: hand; -fx-padding: 10px;";

        this.setStyle(defaultStyle);
        this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
        this.setOnMouseExited(e -> this.setStyle(defaultStyle));

        this.setOnAction(e -> onClick.run());
    }
}
