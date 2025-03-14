package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

// นำเข้าคลาสที่จำเป็น
import com.project.Controller;
import com.project.DatabaseHelper;
import com.project.Expense;
import com.project.User;

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

        // 🔹 ปุ่มลบรายจ่าย
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

        Button editButton = new Button("แก้ไขรายจ่าย");
        editButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            String selectedExpense = expenseListView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                int id = Integer.parseInt(selectedExpense.split(" - ")[0]);

                TextInputDialog dialogDesc = new TextInputDialog();
                dialogDesc.setTitle("แก้ไขข้อมูล");
                dialogDesc.setHeaderText("กรุณากรอกชื่อใหม่");
                Optional<String> newDescription = dialogDesc.showAndWait();

                TextInputDialog dialogAmount = new TextInputDialog();
                dialogAmount.setTitle("แก้ไขข้อมูล");
                dialogAmount.setHeaderText("กรุณากรอกจำนวนเงินใหม่");
                Optional<String> newAmountStr = dialogAmount.showAndWait();

                if (newDescription.isPresent() && newAmountStr.isPresent()) {
                    try {
                        double newAmount = Double.parseDouble(newAmountStr.get());
                        if (controller.editExpense(id, newDescription.get(), newAmount)) {
                            showAlert("สำเร็จ", "แก้ไขข้อมูลเรียบร้อย!");
                            // รีเฟรชรายการหลังแก้ไข
                            expenseListView.getItems().setAll(DatabaseHelper.getAllExpensesList());
                        } else {
                            showAlert("ผิดพลาด", "ไม่พบรายจ่ายที่ต้องการแก้ไข");
                        }
                    } catch (NumberFormatException ex) {
                        showAlert("ข้อผิดพลาด", "กรุณากรอกจำนวนเงินที่ถูกต้อง");
                    }
                }
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, title, expenseListView, deleteButton, editButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 600, 400); // ปรับขนาดหน้าจอเป็น 600x400
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
