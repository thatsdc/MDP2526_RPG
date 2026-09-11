package it.unicam.cs.mpgc.rpg129072.components.Button;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AttackButton extends Button {
    public AttackButton(String label, Runnable onClick) {
        super(label);
        this.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        this.setPrefSize(80, 50);

        String defaultStyle = "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        String hoverStyle = "-fx-background-color: #e0e0e0; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand; -fx-border-radius: 5px; -fx-background-radius: 5px;";

        this.setStyle(defaultStyle);
        this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
        this.setOnMouseExited(e -> this.setStyle(defaultStyle));

        this.setOnAction(e -> onClick.run());
    }
}
