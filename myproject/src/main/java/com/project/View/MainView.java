package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {

    private Controller controller;

    public MainView(Controller controller) {
        this.controller = controller;
    }

    public Scene createMainScene() {
        Label title = new Label("Expense Tracker");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button addExpenseButton = new Button("เพิ่มรายจ่าย");
        Button viewExpensesButton = new Button("ดูรายการทั้งหมด");

        addExpenseButton.setOnAction(e -> controller.showAddExpenseView());
        viewExpensesButton.setOnAction(e -> controller.showViewExpensesView());

        VBox layout = new VBox(15, title, addExpenseButton, viewExpensesButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        return new Scene(layout, 400, 300);
    }
}
