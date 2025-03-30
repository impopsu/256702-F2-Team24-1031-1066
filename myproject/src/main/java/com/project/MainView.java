package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
        VBox layout = new VBox(20); // ลดระยะห่างระหว่างองค์ประกอบ
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px; " +
                "-fx-background-color: #F0F4F8;"); // พื้นหลังสีฟ้าอ่อน (สุภาพ)

        // แสดงยอดรวมรายรับ-รายจ่าย และคงเหลือ
        Label summaryLabel = new Label();
        summaryLabel.setFont(new Font("Arial", 18)); // ลดขนาดฟอนต์ให้เหมาะสม
        summaryLabel.setStyle("-fx-text-fill: #333333;"); // สีข้อความเป็นสีเทาเข้ม
        updateSummary(summaryLabel);

        // ปุ่มต่างๆ พร้อมอิโมติคอน
        Button addExpenseButton = createStyledButton("➕ เพิ่มค่าใช้จ่าย");
        addExpenseButton.setOnAction(e -> controller.showAddExpenseView());

        Button viewExpensesButton = createStyledButton("📋 ดูรายการค่าใช้จ่าย");
        viewExpensesButton.setOnAction(e -> controller.showViewExpensesView());

        Button addCategoryButton = createStyledButton("📂 เพิ่มหมวดหมู่ใหม่");
        addCategoryButton.setOnAction(e -> controller.showAddCategoryView());

        Button deleteCategoryButton = createStyledButton("🗑️ ลบหมวดหมู่");
        deleteCategoryButton.setOnAction(e -> showDeleteCategoryDialog());

        Button searchExpensesButton = createStyledButton("🔍 ค้นหารายการค่าใช้จ่าย");
        searchExpensesButton.setOnAction(e -> controller.showSearchExpensesView());

        Button profileButton = createStyledButton("👤 โปรไฟล์ผู้ใช้");
        profileButton.setOnAction(e -> controller.showUserProfileView());

        Button logoutButton = createStyledButton("🚪 ออกจากระบบ");
        logoutButton.setOnAction(e -> controller.logout());

        // จัดเรียงปุ่มใน GridPane
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(15); // ลดระยะห่างแนวนอนระหว่างปุ่ม
        buttonGrid.setVgap(15); // ลดระยะห่างแนวตั้งระหว่างปุ่ม
        buttonGrid.setAlignment(Pos.CENTER);

        // เพิ่มปุ่มลงใน GridPane
        buttonGrid.add(addExpenseButton, 0, 0);
        buttonGrid.add(viewExpensesButton, 1, 0);
        buttonGrid.add(addCategoryButton, 0, 1);
        buttonGrid.add(deleteCategoryButton, 1, 1);
        buttonGrid.add(searchExpensesButton, 0, 2);
        buttonGrid.add(profileButton, 1, 2);
        buttonGrid.add(logoutButton, 0, 3, 2, 1); // ปุ่ม Logout กินพื้นที่ 2 คอลัมน์

        // เพิ่ม Label และ GridPane ลงใน Layout หลัก
        layout.getChildren().addAll(summaryLabel, buttonGrid);

        // สร้าง Scene และส่งกลับ (ขนาดหน้าจอ 800x600)
        return new Scene(layout, 800, 600);
    }

    private void updateSummary(Label summaryLabel) {
        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double monthlyBudget = controller.getCurrentUser().getMonthlyBudget();
        double remainingBudget = monthlyBudget - totalExpenses;

        summaryLabel.setText(String.format(
                "💰 ยอดรวมรายจ่าย: %.2f บาท\n📅 งบประมาณรายเดือน: %.2f บาท\n🟢 คงเหลือ: %.2f บาท",
                totalExpenses, monthlyBudget, remainingBudget
        ));
    }

    private void showDeleteCategoryDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("ลบหมวดหมู่");
        alert.setHeaderText("เลือกหมวดหมู่ที่ต้องการลบ");
        alert.setContentText("ฟังก์ชันนี้ยังไม่พร้อมใช้งาน");
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; " + // ลดขนาดฟอนต์ให้เหมาะสม
                "-fx-background-color: #E0E0E0; " + // สีเทาอ่อน
                "-fx-text-fill: #333333; " + // ตัวอักษรสีเทาเข้ม
                "-fx-padding: 8px 16px; " + // ลด Padding ให้เหมาะสม
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px;");
        return button;
    }
}