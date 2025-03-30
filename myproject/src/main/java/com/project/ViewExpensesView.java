package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.List;

public class ViewExpensesView {

    private Controller controller;

    public ViewExpensesView(Controller controller) {
        this.controller = controller;
    }

    public Scene createViewExpensesScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        Label titleLabel = new Label("รายการค่าใช้จ่าย");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TableView<Expense> tableView = new TableView<>();

        TableColumn<Expense, Integer> idColumn = new TableColumn<>("ลำดับ");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("คำอธิบาย");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        TableColumn<Expense, Double> amountColumn = new TableColumn<>("จำนวนเงิน");
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        TableColumn<Expense, LocalDate> dateColumn = new TableColumn<>("วันที่");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // เพิ่มคอลัมน์หมวดหมู่
        TableColumn<Expense, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        tableView.getColumns().addAll(idColumn, descriptionColumn, amountColumn, dateColumn, categoryColumn);

        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        tableView.getItems().addAll(expenses);

        Button editButton = new Button("แก้ไขรายการที่เลือก");
        editButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            Expense selectedExpense = tableView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                controller.showEditExpenseView(selectedExpense.getId(), selectedExpense.getDescription(), selectedExpense.getAmount(), selectedExpense.getDate());
            }
        });

        Button deleteButton = new Button("ลบรายการที่เลือก");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            Expense selectedExpense = tableView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                boolean result = DatabaseHelper.deleteExpense(selectedExpense.getId());
                if (result) {
                    tableView.getItems().remove(selectedExpense);
                    showAlert("สำเร็จ", "ลบรายการค่าใช้จ่ายเรียบร้อย!");
                } else {
                    showAlert("ผิดพลาด", "ไม่สามารถลบรายการค่าใช้จ่ายได้");
                }
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        HBox buttonBox = new HBox(10, editButton, deleteButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(titleLabel, tableView, buttonBox);

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
