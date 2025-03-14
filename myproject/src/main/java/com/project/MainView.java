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
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        Button addExpenseButton = new Button("เพิ่มค่าใช้จ่าย");
        addExpenseButton.setOnAction(e -> {
            controller.showAddExpenseView();
        });

        Button viewExpensesButton = new Button("ดูรายการค่าใช้จ่าย");
        viewExpensesButton.setOnAction(e -> {
            controller.showViewExpensesView();
        });

        Button logoutButton = new Button("ออกจากระบบ");
        logoutButton.setOnAction(e -> {
            controller.logout();
        });

        layout.getChildren().addAll(addExpenseButton, viewExpensesButton, logoutButton);
        return new Scene(layout, 400, 300);
    }
}
