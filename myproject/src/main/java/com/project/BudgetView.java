package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class BudgetView {

    private Controller controller;

    public BudgetView(Controller controller) {
        this.controller = controller;
    }

    public Scene createBudgetScene() {
        // Layout หลัก
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #FAF3E0;");

        // Header
        Label headerLabel = new Label("ตั้งค่างบประมาณ");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill: #333333;");

        // แสดงงบประมาณปัจจุบัน
        double currentBudget = controller.getCurrentUser().getMonthlyBudget();
        Label budgetLabel = new Label("งบประมาณปัจจุบัน: " + String.format("%.2f", currentBudget) + " บาท");
        budgetLabel.setFont(new Font("Arial", 18));
        budgetLabel.setStyle("-fx-text-fill: #333333;");

        // ช่องกรอกงบประมาณใหม่
        TextField budgetInput = new TextField();
        budgetInput.setPromptText("ใส่จำนวนงบประมาณใหม่");
        budgetInput.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // ปุ่มบันทึกงบประมาณ
        Button saveButton = new Button("💾 บันทึก");
        saveButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF;");
        saveButton.setOnAction(e -> {
            try {
                double newBudget = Double.parseDouble(budgetInput.getText());
                controller.updateBudget(newBudget); // เรียกใช้เมธอดใน Controller
                budgetLabel.setText("งบประมาณปัจจุบัน: " + String.format("%.2f", newBudget) + " บาท");
                budgetInput.clear();
            } catch (NumberFormatException ex) {
                controller.showAlert("ข้อผิดพลาด", "กรุณาใส่จำนวนเงินที่ถูกต้อง");
            }
        });

        // ปุ่มกลับไปหน้าหลัก
        Button backButton = new Button("⬅️ กลับ");
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #F4C542; -fx-text-fill: #333333;");
        backButton.setOnAction(e -> controller.showMainView()); // กลับไปหน้าหลัก

        // เพิ่มองค์ประกอบใน Layout
        layout.getChildren().addAll(headerLabel, budgetLabel, budgetInput, saveButton, backButton);

        // สร้าง Scene และส่งกลับ
        return new Scene(layout, 800, 600);
    }
}