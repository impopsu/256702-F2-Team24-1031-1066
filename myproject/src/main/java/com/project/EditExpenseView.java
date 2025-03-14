package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class EditExpenseView {

    private Controller controller;

    public EditExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createEditExpenseScene(int expenseId, String currentDescription, double currentAmount, String currentCategory, LocalDate currentDate, String currentType) {
        Label titleLabel = new Label("แก้ไขรายการ");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label descriptionLabel = new Label("คำอธิบาย:");
        TextField descriptionField = new TextField(currentDescription);
        descriptionField.setPromptText("คำอธิบาย");

        Label amountLabel = new Label("จำนวนเงิน:");
        TextField amountField = new TextField(String.valueOf(currentAmount));
        amountField.setPromptText("จำนวนเงิน");

        Label typeLabel = new Label("ประเภท:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("รายรับ", "รายจ่าย");
        typeComboBox.setValue(currentType);

        Label categoryLabel = new Label("หมวดหมู่:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        if (currentType.equals("รายรับ")) {
            categoryComboBox.getItems().addAll(DatabaseHelper.getIncomeCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
        } else {
            categoryComboBox.getItems().addAll(DatabaseHelper.getExpenseCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
        }
        categoryComboBox.setValue(currentCategory);

        typeComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            if (selectedType.equals("รายรับ")) {
                categoryComboBox.getItems().setAll(DatabaseHelper.getIncomeCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
            } else if (selectedType.equals("รายจ่าย")) {
                categoryComboBox.getItems().setAll(DatabaseHelper.getExpenseCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
            }
        });

        Label dateLabel = new Label("วันที่:");
        DatePicker datePicker = new DatePicker(currentDate);

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String newDescription = descriptionField.getText();
            double newAmount = Double.parseDouble(amountField.getText());
            String newCategory = categoryComboBox.getValue();
            LocalDate newDate = datePicker.getValue();
            String newType = typeComboBox.getValue();
            controller.editExpense(expenseId, newDescription, newAmount, newCategory, newDate, newType);
        });

        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, descriptionLabel, descriptionField, amountLabel, amountField, typeLabel, typeComboBox, categoryLabel, categoryComboBox, dateLabel, datePicker, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}