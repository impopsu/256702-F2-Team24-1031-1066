package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditExpenseView {

    private Controller controller;

    public EditExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createEditExpenseScene(int expenseId, String currentDescription, double currentAmount, String currentCategory) {
        Label titleLabel = new Label("แก้ไขรายการค่าใช้จ่าย");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField descriptionField = new TextField(currentDescription);
        descriptionField.setPromptText("คำอธิบาย");

        TextField amountField = new TextField(String.valueOf(currentAmount));
        amountField.setPromptText("จำนวนเงิน");

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(DatabaseHelper.getAllCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
        categoryComboBox.setValue(currentCategory);

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String newDescription = descriptionField.getText();
            double newAmount = Double.parseDouble(amountField.getText());
            String newCategory = categoryComboBox.getValue();
            controller.editExpense(expenseId, newDescription, newAmount, newCategory);
        });

        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, descriptionField, amountField, categoryComboBox, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}