package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class AddExpenseView {

    private Controller controller;

    public AddExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddExpenseScene() {
        Label titleLabel = new Label("เพิ่มรายการ");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label descriptionLabel = new Label("คำอธิบาย:");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("คำอธิบาย");

        Label amountLabel = new Label("จำนวนเงิน:");
        TextField amountField = new TextField();
        amountField.setPromptText("จำนวนเงิน");

        Label dateLabel = new Label("วันที่:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        Label categoryLabel = new Label("หมวดหมู่:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        List<ExpenseCategory> categories = DatabaseHelper.getAllCategories();
        for (ExpenseCategory category : categories) {
            categoryComboBox.getItems().add(category.getName());
        }
        categoryComboBox.setPromptText("เลือกหมวดหมู่");

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String description = descriptionField.getText();
            String amountText = amountField.getText();
            LocalDate date = datePicker.getValue();
            String selectedCategory = categoryComboBox.getValue();

            if (description.isEmpty() || amountText.isEmpty() || date == null || selectedCategory == null) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกข้อมูลให้ครบถ้วน");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                controller.addExpense(description, amount, date); // หมวดหมู่สามารถเพิ่มในฐานข้อมูลได้หากจำเป็น
                controller.showViewExpensesView();
            } catch (NumberFormatException ex) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกจำนวนเงินให้ถูกต้อง");
            }
        });

        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, descriptionLabel, descriptionField, amountLabel, amountField, dateLabel, datePicker, categoryLabel, categoryComboBox, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 1024, 768); // ปรับขนาดหน้าจอเป็น 1024x768
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}