package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // กราฟแสดงการใช้จ่ายรายวัน
        LineChart<String, Number> dailyChart = createDailyExpenseChart();

        // กราฟแสดงการใช้จ่ายแยกตามหมวดหมู่
        PieChart categoryChart = createCategoryExpenseChart();

        // ปุ่มกลับไปหน้าหลัก
        Button backButton = new Button("⬅️ กลับ");
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #F4C542; -fx-text-fill: #333333;");
        backButton.setOnAction(e -> controller.showMainView());

        layout.getChildren().addAll(headerLabel, dailyChart, categoryChart, backButton);

        return new Scene(layout, 800, 600);
    }

    private LineChart<String, Number> createDailyExpenseChart() {
        // แกน X และ Y
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("วันที่");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("จำนวนเงิน (บาท)");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("การใช้จ่ายรายวัน");

        // ดึงข้อมูลการใช้จ่ายรายวัน
        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        Map<LocalDate, Double> dailyExpenses = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getDate, Collectors.summingDouble(Expense::getAmount)));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("การใช้จ่ายรายวัน");

        dailyExpenses.forEach((date, amount) -> {
            series.getData().add(new XYChart.Data<>(date.toString(), amount));
        });

        lineChart.getData().add(series);
        return lineChart;
    }

    private PieChart createCategoryExpenseChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("การใช้จ่ายแยกตามหมวดหมู่");

        // ดึงข้อมูลการใช้จ่ายแยกตามหมวดหมู่
        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        Map<String, Double> categoryExpenses = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

        categoryExpenses.forEach((category, amount) -> {
            PieChart.Data slice = new PieChart.Data(category, amount);
            pieChart.getData().add(slice);
        });

        return pieChart;
    }
}