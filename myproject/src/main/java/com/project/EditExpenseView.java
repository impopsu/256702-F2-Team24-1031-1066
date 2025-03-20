package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    public Scene createEditExpenseScene(int expenseId, String currentDescription, double currentAmount, LocalDate currentDate) {
        Label titleLabel = new Label("แก้ไขรายการ");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label descriptionLabel = new Label("คำอธิบาย:");
        TextField descriptionField = new TextField(currentDescription);
        descriptionField.setPromptText("คำอธิบาย");

        Label amountLabel = new Label("จำนวนเงิน:");
        TextField amountField = new TextField(String.valueOf(currentAmount));
        amountField.setPromptText("จำนวนเงิน");

        Label dateLabel = new Label("วันที่:");
        DatePicker datePicker = new DatePicker(currentDate);

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String newDescription = descriptionField.getText();
            double newAmount = Double.parseDouble(amountField.getText());
            LocalDate newDate = datePicker.getValue();
            controller.editExpense(expenseId, newDescription, newAmount, newDate);
        });

        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, descriptionLabel, descriptionField, amountLabel, amountField, dateLabel, datePicker, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 1024, 768); // ปรับขนาดหน้าจอเป็น 1024x768
    }
}