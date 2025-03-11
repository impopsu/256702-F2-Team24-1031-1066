package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {

    private Stage stage;

    public MainView(Stage stage) {
        this.stage = stage;
    }

    public void showMainScene() {
        Label title = new Label("Expense Tracker");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button addExpenseButton = new Button("เพิ่มรายจ่าย");
        Button viewExpensesButton = new Button("ดูรายการทั้งหมด");

        addExpenseButton.setOnAction(e -> {
            AddExpenseView addExpenseView = new AddExpenseView();
            stage.setScene(addExpenseView.createScene(this));
        });

        viewExpensesButton.setOnAction(e -> {
            ViewExpensesView viewExpensesView = new ViewExpensesView();
            stage.setScene(viewExpensesView.createScene(this));
        });

        VBox layout = new VBox(15, title, addExpenseButton, viewExpensesButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
    }
}
