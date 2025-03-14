package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddExpenseView {

    private Controller controller;

    public AddExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddExpenseScene() {
        Label title = new Label("เพิ่มค่าใช้จ่าย");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("รายละเอียด");
        descriptionField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField amountField = new TextField();
        amountField.setPromptText("จำนวนเงิน");
        amountField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("หมวดหมู่");
        categoryComboBox.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        for (ExpenseCategory category : DatabaseHelper.getAllCategories()) {
            categoryComboBox.getItems().add(category.getName());
        }

        Button addButton = new Button("เพิ่ม");
        addButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setOnAction(e -> {
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryComboBox.getValue();
            controller.addExpense(description, amount, category);
        });

        VBox layout = new VBox(15, title, descriptionField, amountField, categoryComboBox, addButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}
