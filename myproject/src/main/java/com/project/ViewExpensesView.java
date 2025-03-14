package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
                String currentCategory = parts[3];
                LocalDate currentDate = LocalDate.parse(parts[4]);
                String currentType = parts[5];
                controller.showEditExpenseView(expenseId, currentDescription, currentAmount, currentCategory, currentDate, currentType);
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
