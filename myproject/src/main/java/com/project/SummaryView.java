package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class SummaryView {

    private Controller controller;

    public SummaryView(Controller controller) {
        this.controller = controller;
    }

    public Scene createSummaryScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #FAF3E0;");

        // Header
        Label headerLabel = new Label("สรุปข้อมูลการใช้จ่าย");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill: #333333;");

        // ตารางแสดงรายการรายจ่ายย้อนหลัง
        TableView<Expense> tableView = new TableView<>();
        tableView.setPrefWidth(600);

        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("คำอธิบาย");
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
        descriptionColumn.setPrefWidth(200);

        TableColumn<Expense, Double> amountColumn = new TableColumn<>("จำนวนเงิน");
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        amountColumn.setPrefWidth(100);

        TableColumn<Expense, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());
        categoryColumn.setPrefWidth(150);

        TableColumn<Expense, String> dateColumn = new TableColumn<>("วันที่");
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty().asString());
        dateColumn.setPrefWidth(150);

        tableView.getColumns().addAll(descriptionColumn, amountColumn, categoryColumn, dateColumn);

        // ดึงข้อมูลจากฐานข้อมูล
        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        tableView.getItems().addAll(expenses);

        // คำนวณยอดรวมรายจ่าย
        double totalExpense = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        // ดึงงบประมาณปัจจุบันของผู้ใช้
        double budget = controller.getCurrentUser().getMonthlyBudget();

        // คำนวณยอดคงเหลือ
        double balance = budget - totalExpense;

        // แสดงยอดรวมรายจ่าย
        Label expenseLabel = new Label("ยอดรวมรายจ่าย: " + String.format("%.2f", totalExpense) + " บาท");
        expenseLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #F44336;");

        // แสดงงบประมาณ
        Label budgetLabel = new Label("งบประมาณ: " + String.format("%.2f", budget) + " บาท");
        budgetLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4CAF50;");

        // แสดงยอดคงเหลือ
        Label balanceLabel = new Label("ยอดคงเหลือ: " + String.format("%.2f", balance) + " บาท");
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");

        // ปุ่มกลับไปหน้าหลัก
        Button backButton = new Button("⬅️ กลับ");
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #F4C542; -fx-text-fill: #333333;");
        backButton.setOnAction(e -> controller.showMainView());

        layout.getChildren().addAll(headerLabel, tableView, expenseLabel, budgetLabel, balanceLabel, backButton);

        return new Scene(layout, 800, 600);
    }
}