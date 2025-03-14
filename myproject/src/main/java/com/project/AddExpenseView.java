package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddExpenseView {

    private Controller controller;

    public AddExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddExpenseScene() {
        Label title = new Label("เพิ่มรายจ่าย");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField expenseNameField = new TextField();
        expenseNameField.setPromptText("ชื่อรายการ");
        expenseNameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField expenseAmountField = new TextField();
        expenseAmountField.setPromptText("จำนวนเงิน");
        expenseAmountField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String name = expenseNameField.getText();
            String amount = expenseAmountField.getText();
            try {
                double expenseAmount = Double.parseDouble(amount);
                controller.addExpense(name, expenseAmount);
            } catch (NumberFormatException ex) {
                System.out.println("จำนวนเงินไม่ถูกต้อง");
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, title, expenseNameField, expenseAmountField, saveButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 600, 400); // ปรับขนาดหน้าจอเป็น 600x400
    }
}
