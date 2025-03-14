package com.project;

import com.project.DatabaseHelper;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // สร้างตารางฐานข้อมูลหากยังไม่มี
        DatabaseHelper.createTable();

        // สร้าง Controller และเริ่มต้นแอปพลิเคชัน
        Controller controller = new Controller(stage);
        controller.start();
    }

    public static void main(String[] args) {
        // เริ่มต้นแอปพลิเคชัน JavaFX
        launch(args);
    }
}
