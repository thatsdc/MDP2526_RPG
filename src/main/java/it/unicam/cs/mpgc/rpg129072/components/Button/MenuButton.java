package it.unicam.cs.mpgc.rpg129072.components.Button;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MenuButton extends Button {
    public MenuButton(String text, Runnable onClick) {
        super(text);
        this.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Default styling
        String defaultStyle = "-fx-background-color: transparent; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 2px; " +
                "-fx-padding: 10 30 10 30; " +
                "-fx-cursor: hand;";

        // Hover styling
        String hoverStyle = "-fx-background-color: white; " +
                "-fx-text-fill: #2b2b2b; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 2px; " +
                "-fx-padding: 10 30 10 30; " +
                "-fx-cursor: hand;";

        this.setStyle(defaultStyle);

        // Hover effects
        this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
        this.setOnMouseExited(e -> this.setStyle(defaultStyle));

        // Set function to execute on click
        this.setOnAction(e -> onClick.run());
    }
}
