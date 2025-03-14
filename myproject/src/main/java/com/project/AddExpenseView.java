package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class AddExpenseView {

    private Controller controller;

    public AddExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddExpenseScene() {
        Label titleLabel = new Label("เพิ่มรายการ");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label typeLabel = new Label("ประเภท:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("รายรับ", "รายจ่าย");

        Label descriptionLabel = new Label("คำอธิบาย:");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("คำอธิบาย");

        Label amountLabel = new Label("จำนวนเงิน:");
        TextField amountField = new TextField();
        amountField.setPromptText("จำนวนเงิน");

        Label categoryLabel = new Label("หมวดหมู่:");
        ComboBox<String> categoryComboBox = new ComboBox<>();

        typeComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            if (selectedType.equals("รายรับ")) {
                categoryComboBox.getItems().setAll(DatabaseHelper.getIncomeCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
            } else if (selectedType.equals("รายจ่าย")) {
                categoryComboBox.getItems().setAll(DatabaseHelper.getExpenseCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
            }
        });

        Label dateLabel = new Label("วันที่:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String description = descriptionField.getText();
            String amountText = amountField.getText();
            String category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();
            String type = typeComboBox.getValue();

            if (description.isEmpty() || amountText.isEmpty() || category == null || date == null || type == null) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกข้อมูลให้ครบถ้วน");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                controller.addExpense(description, amount, category, date, type);
                controller.showViewExpensesView(); // เพิ่มการแสดงหน้าดูรายการค่าใช้จ่ายหลังจากบันทึก
            } catch (NumberFormatException ex) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกจำนวนเงินให้ถูกต้อง");
            }
        });

        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, typeLabel, typeComboBox, descriptionLabel, descriptionField, amountLabel, amountField, categoryLabel, categoryComboBox, dateLabel, datePicker, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER_LEFT);
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