package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.List;

public class AddExpenseView {

    private Controller controller;

    public AddExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddExpenseScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #FAF3E0;");

        Label headerLabel = new Label("เพิ่มค่าใช้จ่าย");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill: #333333;");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("คำอธิบาย");

        TextField amountField = new TextField();
        amountField.setPromptText("จำนวนเงิน");

        // เพิ่ม DatePicker สำหรับเลือกวันที่
        Label dateLabel = new Label("วันที่:");
        dateLabel.setStyle("-fx-text-fill: #333333;");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now()); // ตั้งค่าเริ่มต้นเป็นวันที่ปัจจุบัน

        // เพิ่ม ComboBox สำหรับเลือกหมวดหมู่
        Label categoryLabel = new Label("หมวดหมู่:");
        categoryLabel.setStyle("-fx-text-fill: #333333;");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("เลือกหมวดหมู่");

        // ดึงรายการหมวดหมู่จาก DatabaseHelper
        List<ExpenseCategory> categories = DatabaseHelper.getAllCategories();
        for (ExpenseCategory category : categories) {
            categoryComboBox.getItems().add(category.getName());
        }

        Button saveButton = new Button("💾 บันทึก");
        saveButton.setOnAction(e -> {
            try {
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                LocalDate date = datePicker.getValue(); // รับค่าวันที่ที่เลือก
                String category = categoryComboBox.getValue(); // รับค่าหมวดหมู่ที่เลือก

                if (date == null) {
                    controller.showAlert("ข้อผิดพลาด", "กรุณาเลือกวันที่");
                    return;
                }

                if (category == null || category.isEmpty()) {
                    controller.showAlert("ข้อผิดพลาด", "กรุณาเลือกหมวดหมู่");
                    return;
                }

                controller.addExpense(description, amount, date, category);
                controller.checkBudgetExceeded(); // ตรวจสอบการใช้จ่ายเกินงบประมาณ
                controller.showMainView(); // กลับไปหน้าหลักหลังบันทึก
            } catch (NumberFormatException ex) {
                controller.showAlert("ข้อผิดพลาด", "กรุณาใส่จำนวนเงินที่ถูกต้อง");
            }
        });

        Button backButton = new Button("⬅️ กลับ");
        backButton.setOnAction(e -> controller.showMainView()); // กลับไปหน้าหลัก

        layout.getChildren().addAll(headerLabel, descriptionField, amountField, dateLabel, datePicker, categoryLabel, categoryComboBox, saveButton, backButton);

        return new Scene(layout, 800, 600);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}