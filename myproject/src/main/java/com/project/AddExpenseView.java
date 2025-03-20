package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class ViewExpensesView {

    private Controller controller;

    public ViewExpensesView(Controller controller) {
        this.controller = controller;
    }

    public Scene createViewExpensesScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        ListView<String> expensesListView = new ListView<>();
        expensesListView.getItems().addAll(DatabaseHelper.getAllExpensesList());

        Button editButton = new Button("แก้ไขรายการที่เลือก");
        editButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            String selectedExpense = expensesListView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                String[] parts = selectedExpense.split(" - ");
                int expenseId = Integer.parseInt(parts[0]);
                String currentDescription = parts[1];
                double currentAmount = Double.parseDouble(parts[2]);
                LocalDate currentDate = LocalDate.parse(parts[3]);
                controller.showEditExpenseView(expenseId, currentDescription, currentAmount, currentDate);
            }
        });

        Button deleteButton = new Button("ลบรายการที่เลือก");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            String selectedExpense = expensesListView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                String[] parts = selectedExpense.split(" - ");
                int expenseId = Integer.parseInt(parts[0]);
                boolean result = DatabaseHelper.deleteExpense(expenseId);
                if (result) {
                    expensesListView.getItems().remove(selectedExpense);
                    showAlert("สำเร็จ", "ลบรายการค่าใช้จ่ายเรียบร้อย!");
                } else {
                    showAlert("ผิดพลาด", "ไม่สามารถลบรายการค่าใช้จ่ายได้");
                }
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        layout.getChildren().addAll(expensesListView, editButton, deleteButton, backButton);

        return new Scene(layout, 1024, 768); // ปรับขนาดหน้าจอเป็น 1024x768
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

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

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String description = descriptionField.getText();
            String amountText = amountField.getText();
            LocalDate date = datePicker.getValue();

            if (description.isEmpty() || amountText.isEmpty() || date == null) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกข้อมูลให้ครบถ้วน");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                controller.addExpense(description, amount, date);
                controller.showViewExpensesView(); // เพิ่มการแสดงหน้าดูรายการค่าใช้จ่ายหลังจากบันทึก
            } catch (NumberFormatException ex) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกจำนวนเงินให้ถูกต้อง");
            }
        });

        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, descriptionLabel, descriptionField, amountLabel, amountField, dateLabel, datePicker, saveButton, cancelButton);
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