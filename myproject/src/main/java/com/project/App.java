package com.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MainScene mainScene = new MainScene();
        Scene scene = mainScene.createScene();
        stage.setScene(scene);
        stage.setTitle("JavaFX App");
        stage.show();
    }
}