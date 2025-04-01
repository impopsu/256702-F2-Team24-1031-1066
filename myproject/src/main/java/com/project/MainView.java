package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class MainView {

    private Controller controller;

    public MainView(Controller controller) {
        this.controller = controller;
    }

    public Scene createMainScene() {
        // Layout หลัก
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color:rgb(21, 27, 87);");

        // Header
        Label headerLabel = new Label("@PersonalExpenseTracker");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill:rgb(248, 247, 247);");

        // ดึงข้อมูลยอดรวมค่าใช้จ่าย, งบประมาณ และคงเหลือ
        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double budget = controller.getCurrentUser().getMonthlyBudget();
        double balance = budget - totalExpense;

        // แสดงยอดรวมค่าใช้จ่าย
        Label totalExpenseLabel = new Label("ยอดรวมค่าใช้จ่าย: " + String.format("%.2f", totalExpense) + " บาท");
        totalExpenseLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #F44336;");

        // แสดงงบประมาณ
        Label budgetLabel = new Label("งบประมาณ: " + String.format("%.2f", budget) + " บาท");
        budgetLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4CAF50;");

        // แสดงยอดคงเหลือ
        Label balanceLabel = new Label("ยอดคงเหลือ: " + String.format("%.2f", balance) + " บาท");
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFFFFF;");

        // ปุ่มต่างๆ พร้อมอิโมติคอน
        Button addExpenseButton = createStyledButton("➕ เพิ่มค่าใช้จ่าย");
        addExpenseButton.setOnAction(e -> {
            System.out.println("กดปุ่มเพิ่มค่าใช้จ่าย"); // Debugging
            controller.showAddExpenseView();
        });

        Button viewExpensesButton = createStyledButton("📋 ดูรายการค่าใช้จ่าย");
        viewExpensesButton.setOnAction(e -> {
            System.out.println("กดปุ่มดูรายการค่าใช้จ่าย"); // Debugging
            controller.showViewExpensesView();
        });

        Button addCategoryButton = createStyledButton("📂 เพิ่มหมวดหมู่ใหม่");
        addCategoryButton.setOnAction(e -> controller.showAddCategoryView());

        Button deleteCategoryButton = createStyledButton("🗑️ ลบหมวดหมู่");
        deleteCategoryButton.setOnAction(e -> showDeleteCategoryDialog());

        Button searchExpensesButton = createStyledButton("🔍 ค้นหารายการค่าใช้จ่าย");
        searchExpensesButton.setOnAction(e -> controller.showSearchExpensesView());

        Button profileButton = createStyledButton("👤 โปรไฟล์ผู้ใช้");
        profileButton.setOnAction(e -> controller.showUserProfileView());

        Button budgetButton = createStyledButton("💰 งบประมาณ");
        budgetButton.setOnAction(e -> controller.showBudgetView());

        Button logoutButton = createStyledButton("➡️ ออกจากระบบ");
        logoutButton.setOnAction(e -> controller.logout());

        Button summaryButton = createStyledButton("📊 สรุปข้อมูลการใช้จ่าย");
        summaryButton.setOnAction(e -> controller.showSummaryView());

        // เพิ่มปุ่มรีเซ็ตข้อมูล
        Button resetButton = createStyledButton("🔄 รีเซ็ตข้อมูล");
        resetButton.setOnAction(e -> {
            DatabaseHelper.clearAllExpenses(); // ลบข้อมูลค่าใช้จ่ายทั้งหมด
            controller.getCurrentUser().setMonthlyBudget(0); // รีเซ็ตงบประมาณเป็น 0

            // อัปเดต UI
            totalExpenseLabel.setText("ยอดรวมค่าใช้จ่าย: 0.00 บาท");
            budgetLabel.setText("งบประมาณ: 0.00 บาท");
            balanceLabel.setText("ยอดคงเหลือ: 0.00 บาท");

            System.out.println("ข้อมูลถูกรีเซ็ตแล้ว");
        });

        // จัดเรียงปุ่มใน GridPane
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(15); // ระยะห่างแนวนอนระหว่างปุ่ม (ค่าเดิม)
        buttonGrid.setVgap(15); // ระยะห่างแนวตั้งระหว่างปุ่ม (ค่าเดิม)
        buttonGrid.setAlignment(Pos.CENTER); // จัดตำแหน่งให้อยู่ตรงกลาง

        // เพิ่มปุ่มลงใน GridPane
        buttonGrid.add(addExpenseButton, 0, 0);
        buttonGrid.add(viewExpensesButton, 1, 0);
        buttonGrid.add(addCategoryButton, 0, 1);
        buttonGrid.add(deleteCategoryButton, 1, 1);
        buttonGrid.add(searchExpensesButton, 0, 2);
        buttonGrid.add(profileButton, 1, 2);
        buttonGrid.add(budgetButton, 0, 3);
        buttonGrid.add(summaryButton, 1, 3);
        buttonGrid.add(logoutButton, 0, 4);
        buttonGrid.add(resetButton, 1, 4);

        // เพิ่ม Header, ข้อมูลยอดรวม และ GridPane ลงใน Layout หลัก
        layout.getChildren().addAll(headerLabel, totalExpenseLabel, budgetLabel, balanceLabel, buttonGrid);

        // สร้าง Scene และส่งกลับ (ขนาดหน้าจอ 800x600)
        return new Scene(layout, 800, 600);
    }

    private void showDeleteCategoryDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("ลบหมวดหมู่");
        alert.setHeaderText("เลือกหมวดหมู่ที่ต้องการลบ");
        alert.setContentText("ฟังก์ชันนี้ยังไม่พร้อมใช้งาน");
        alert.showAndWait();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16px; " +
                "-fx-background-color:rgb(235, 209, 11); " + // สีเขียวอ่อน (Pale Green)
                "-fx-text-fill:rgb(3, 3, 3); " + // ตัวอักษรสีขาว
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px;");
        return button;
    }
}