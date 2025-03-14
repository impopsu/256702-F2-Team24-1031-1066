package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainView {

    private Controller controller;

    public MainView(Controller controller) {
        this.controller = controller;
    }

    public Scene createMainScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        Button addExpenseButton = new Button("เพิ่มค่าใช้จ่าย");
        addExpenseButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addExpenseButton.setOnAction(e -> {
            controller.showAddExpenseView();
        });

        Button viewExpensesButton = new Button("ดูรายการค่าใช้จ่าย");
        viewExpensesButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        viewExpensesButton.setOnAction(e -> {
            controller.showViewExpensesView();
        });

        Button logoutButton = new Button("ออกจากระบบ");
        logoutButton.setStyle("-fx-font-size: 16px; -fx-background-color: #f44336; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> {
            controller.logout();
        });

        layout.getChildren().addAll(addExpenseButton, viewExpensesButton, logoutButton);
        return new Scene(layout, 600, 400); // ปรับขนาดหน้าจอเป็น 600x400
    }
}
