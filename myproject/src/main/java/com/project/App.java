package com.project;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // สร้างฐานข้อมูลและตาราง
        DatabaseHelper.createTable();

        MainView mainView = new MainView(stage);
        mainView.showMainScene();

        stage.setTitle("Expense Tracker");
        stage.show();
    }
}
