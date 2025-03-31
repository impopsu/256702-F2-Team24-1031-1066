package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.time.LocalDate;

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

        Button saveButton = new Button("💾 บันทึก");
        saveButton.setOnAction(e -> {
            try {
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                LocalDate date = LocalDate.now(); // ใช้วันที่ปัจจุบัน
                controller.addExpense(description, amount, date);
                controller.showMainView(); // กลับไปหน้าหลักหลังบันทึก
            } catch (NumberFormatException ex) {
                controller.showAlert("ข้อผิดพลาด", "กรุณาใส่จำนวนเงินที่ถูกต้อง");
            }
        });

        Button backButton = new Button("⬅️ กลับ");
        backButton.setOnAction(e -> controller.showMainView()); // กลับไปหน้าหลัก

        layout.getChildren().addAll(headerLabel, descriptionField, amountField, saveButton, backButton);

        return new Scene(layout, 800, 600);
    }
}