module it.unicam.cs.mpgc.rpg129072 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    opens it.unicam.cs.mpgc.rpg129072.persistence.models to com.google.gson;

    opens it.unicam.cs.mpgc.rpg129072 to javafx.fxml;
    exports it.unicam.cs.mpgc.rpg129072;
    exports it.unicam.cs.mpgc.rpg129072.controllers;
    opens it.unicam.cs.mpgc.rpg129072.controllers to javafx.fxml;
    exports it.unicam.cs.mpgc.rpg129072.managers;
    opens it.unicam.cs.mpgc.rpg129072.managers to javafx.fxml;
}