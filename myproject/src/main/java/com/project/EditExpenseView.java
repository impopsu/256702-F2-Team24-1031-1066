package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class EditExpenseView {

    private Controller controller;
    private Expense expense;

    public EditExpenseView(Controller controller, Expense expense) {
        this.controller = controller;
        this.expense = expense;
    }

    public Scene createEditExpenseScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        Label titleLabel = new Label("แก้ไขรายการค่าใช้จ่าย");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // ฟิลด์คำอธิบาย
        TextField descriptionField = new TextField(expense.getDescription());

        // ฟิลด์จำนวนเงิน
        TextField amountField = new TextField(String.valueOf(expense.getAmount()));

        // ตัวเลือกวันที่
        DatePicker datePicker = new DatePicker(expense.getDate());

        // ComboBox สำหรับเลือกหมวดหมู่
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("เลือกหมวดหมู่");

        // ดึงรายการหมวดหมู่จากฐานข้อมูล
        List<ExpenseCategory> categories = DatabaseHelper.getAllCategories();
        for (ExpenseCategory category : categories) {
            categoryComboBox.getItems().add(category.getName());
        }

        // ตั้งค่าหมวดหมู่ปัจจุบัน
        categoryComboBox.setValue(expense.getCategory());

        // ปุ่มบันทึก
        Button saveButton = new Button("บันทึก");
        saveButton.setOnAction(e -> {
            try {
                expense.setDescription(descriptionField.getText());
                expense.setAmount(Double.parseDouble(amountField.getText()));
                expense.setDate(datePicker.getValue());
                expense.setCategory(categoryComboBox.getValue());

                if (expense.getCategory() == null || expense.getCategory().isEmpty()) {
                    showAlert("ข้อผิดพลาด", "กรุณาเลือกหมวดหมู่");
                    return;
                }

                DatabaseHelper.updateExpense(expense); // อัปเดตในฐานข้อมูล
                controller.showViewExpensesView(); // กลับไปหน้าดูรายการ
            } catch (NumberFormatException ex) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกจำนวนเงินให้ถูกต้อง");
            }
        });

        // ปุ่มกลับ
        Button backButton = new Button("⬅️ กลับ");
        backButton.setOnAction(e -> controller.showViewExpensesView());

        layout.getChildren().addAll(titleLabel, descriptionField, amountField, datePicker, categoryComboBox, saveButton, backButton);

        return new Scene(layout, 800, 600);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}