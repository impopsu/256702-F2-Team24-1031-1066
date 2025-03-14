package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class ViewExpensesView {

    private Controller controller;

    public ViewExpensesView(Controller controller) {
        this.controller = controller;
    }

    public Scene createViewExpensesScene() {
        Label title = new Label("ดูรายการทั้งหมด");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        ListView<String> expenseListView = new ListView<>();
        List<String> expenses = DatabaseHelper.getAllExpensesList();
        expenseListView.getItems().addAll(expenses);
        expenseListView.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // ปุ่มลบรายจ่าย
        Button deleteButton = new Button("ลบรายจ่ายที่เลือก");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            String selectedExpense = expenseListView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                int id = Integer.parseInt(selectedExpense.split(" - ")[0]);
                if (DatabaseHelper.deleteExpense(id)) {
                    expenseListView.getItems().remove(selectedExpense);
                    showAlert("สำเร็จ", "ลบรายจ่ายเรียบร้อย!");
                } else {
                    showAlert("ผิดพลาด", "ไม่สามารถลบรายจ่ายได้");
                }
            }
        });

        // ปุ่มแก้ไขรายจ่าย
        Button editButton = new Button("แก้ไขรายการที่เลือก");
        editButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            String selectedExpense = expenseListView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                String[] parts = selectedExpense.split(" - ");
                int expenseId = Integer.parseInt(parts[0]);
                String currentDescription = parts[1];
                double currentAmount = Double.parseDouble(parts[2]);
                String currentCategory = parts[3];
                controller.showEditExpenseView(expenseId, currentDescription, currentAmount, currentCategory);
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, title, expenseListView, deleteButton, editButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
