package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class MainScene {
    public Scene createScene() {
        Label title = new Label("Hello, JavaFX!");
        Scene scene = new Scene(title, 400, 200);
        title.setAlignment(Pos.CENTER);
        return scene;
    }
}