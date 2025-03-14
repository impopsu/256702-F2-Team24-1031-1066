package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class SearchExpensesView {

    private Controller controller;

    public SearchExpensesView(Controller controller) {
        this.controller = controller;
    }

    public Scene createSearchExpensesScene() {
        Label titleLabel = new Label("ค้นหารายการค่าใช้จ่าย");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label searchLabel = new Label("ค้นหา:");
        TextField searchField = new TextField();
        searchField.setPromptText("ค้นหา...");

        ListView<String> expensesListView = new ListView<>();

        Button searchButton = new Button("ค้นหา");
        searchButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            List<String> filteredExpenses = DatabaseHelper.getAllExpensesList().stream()
                    .filter(expense -> expense.toLowerCase().contains(query))
                    .collect(Collectors.toList());
            expensesListView.getItems().setAll(filteredExpenses);
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, searchLabel, searchField, searchButton, expensesListView, backButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}