package com.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // สร้าง TableView
        TableView<Expense> tableView = new TableView<>();
        tableView.setPrefWidth(800);

        // สร้างคอลัมน์ "ลำดับ"
        TableColumn<Expense, Integer> idColumn = new TableColumn<>("ลำดับ");
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        idColumn.setPrefWidth(50);

        // สร้างคอลัมน์ "คำอธิบาย"
        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("คำอธิบาย");
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
        descriptionColumn.setPrefWidth(200);

        // สร้างคอลัมน์ "หมวดหมู่"
        TableColumn<Expense, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());
        categoryColumn.setPrefWidth(150);

        // สร้างคอลัมน์ "จำนวนเงิน"
        TableColumn<Expense, Double> amountColumn = new TableColumn<>("จำนวนเงิน");
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        amountColumn.setPrefWidth(100);

        // สร้างคอลัมน์ "วันที่"
        TableColumn<Expense, LocalDate> dateColumn = new TableColumn<>("วันที่");
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());
        dateColumn.setPrefWidth(100);

        // เพิ่มคอลัมน์ทั้งหมดใน TableView
        tableView.getColumns().addAll(idColumn, descriptionColumn, categoryColumn, amountColumn, dateColumn);

        // อัปเดตข้อมูลใน TableView
        updateExpensesTable(tableView);

        // ปุ่มลบ
        Button deleteButton = new Button("ลบ");
        deleteButton.setOnAction(e -> {
            Expense selectedExpense = tableView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                boolean success = DatabaseHelper.deleteExpense(selectedExpense.getId()); // ลบจากฐานข้อมูล
                if (success) {
                    updateExpensesTable(tableView); // อัปเดตตาราง
                } else {
                    showAlert("ข้อผิดพลาด", "ไม่สามารถลบรายการได้");
                }
            } else {
                showAlert("ข้อผิดพลาด", "กรุณาเลือกรายการที่ต้องการลบ");
            }
        });

        // ปุ่มแก้ไข
        Button editButton = new Button("แก้ไข");
        editButton.setOnAction(e -> {
            Expense selectedExpense = tableView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                controller.showEditExpenseView(selectedExpense); // เปิดหน้าจอแก้ไข
            } else {
                showAlert("ข้อผิดพลาด", "กรุณาเลือกรายการที่ต้องการแก้ไข");
            }
        });

        // ปุ่มกลับ
        Button backButton = new Button("⬅️ กลับ");
        backButton.setOnAction(e -> controller.showMainView());

        // จัดปุ่มใน HBox
        HBox buttonBox = new HBox(10, deleteButton, editButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(titleLabel, tableView, buttonBox);

        return new Scene(layout, 800, 600);
    }

    private void updateExpensesTable(TableView<Expense> tableView) {
        List<Expense> expenses = DatabaseHelper.getAllExpenses(); // ดึงข้อมูลจากฐานข้อมูล
        ObservableList<Expense> observableExpenses = FXCollections.observableArrayList(expenses);
        tableView.setItems(observableExpenses); // ตั้งค่าข้อมูลใน TableView
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
