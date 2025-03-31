package com.project;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // สร้างตารางฐานข้อมูลหากยังไม่มี
        DatabaseHelper.createTable();
        DatabaseHelper.createExpensesTable(); // สร้างตาราง expenses
        DatabaseHelper.createCategoriesTable(); // สร้างตาราง categories
        DatabaseHelper.addDefaultCategories(); // เพิ่มหมวดหมู่เริ่มต้น

        // สร้าง Controller และเริ่มต้นแอปพลิเคชัน
        Controller controller = new Controller(stage);
        controller.start();
    }

    public static void main(String[] args) {
        // เริ่มต้นแอปพลิเคชัน JavaFX
        launch(args);
    }
}
