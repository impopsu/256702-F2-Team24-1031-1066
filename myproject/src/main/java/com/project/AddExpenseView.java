package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddExpenseView {

    public Scene createScene(MainView mainView) {
        Label title = new Label("เพิ่มรายจ่าย");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField expenseNameField = new TextField();
        expenseNameField.setPromptText("ชื่อรายการ");

        TextField expenseAmountField = new TextField();
        expenseAmountField.setPromptText("จำนวนเงิน");

        Button saveButton = new Button("บันทึก");
        saveButton.setOnAction(e -> {
            String name = expenseNameField.getText();
            String amount = expenseAmountField.getText();
            DatabaseHelper.addExpense(name, amount); // เพิ่มข้อมูลลงในฐานข้อมูล
            System.out.println("บันทึกรายจ่าย: " + name + " - " + amount);
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setOnAction(e -> {
            mainView.showMainScene();
        });

        VBox layout = new VBox(10, title, expenseNameField, expenseAmountField, saveButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        return new Scene(layout, 400, 300);
    }
}
