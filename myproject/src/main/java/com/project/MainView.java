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
    private Label summaryLabel; // ย้าย summaryLabel มาเป็นตัวแปรระดับคลาส

    public MainView(Controller controller) {
        this.controller = controller;
    }

    public Scene createMainScene() {
        // Layout หลัก
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; " +
                "-fx-background-color:rgb(188, 57, 118);"); // พื้นหลังสีชมพูอ่อน (Mistly Rose)

        // Header
        Label headerLabel = new Label("@PersonalExpenseTracker");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill:rgb(16, 16, 16);"); // สีข้อความเป็นสีเขียวเข้ม (Sea Green)

        // แสดงยอดรวมรายรับ-รายจ่าย และคงเหลือ
        summaryLabel = new Label("ยอดรวมรายจ่าย: 0.00 บาท\nงบประมาณรายเดือน: 0.00 บาท\nคงเหลือ: 0.00 บาท");
        summaryLabel.setFont(new Font("Arial", 18));
        summaryLabel.setStyle("-fx-text-fill:rgb(17, 18, 18);"); // สีข้อความเป็นสีเขียวเข้ม (Sea Green)
        summaryLabel.setAlignment(Pos.CENTER);

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

        // จัดเรียงปุ่มใน GridPane
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(15); // ระยะห่างแนวนอนระหว่างปุ่ม
        buttonGrid.setVgap(15); // ระยะห่างแนวตั้งระหว่างปุ่ม
        buttonGrid.setAlignment(Pos.CENTER);

        // เพิ่มปุ่มลงใน GridPane
        buttonGrid.add(addExpenseButton, 0, 0);
        buttonGrid.add(viewExpensesButton, 1, 0);
        buttonGrid.add(addCategoryButton, 0, 1);
        buttonGrid.add(deleteCategoryButton, 1, 1); // ปุ่มลบหมวดหมู่
        buttonGrid.add(searchExpensesButton, 0, 2);
        buttonGrid.add(profileButton, 1, 2);
        buttonGrid.add(budgetButton, 0, 3); // ปุ่มงบประมาณ
        buttonGrid.add(logoutButton, 1, 3); // ปุ่ม Logout

        // เพิ่ม Header, Summary และ GridPane ลงใน Layout หลัก
        layout.getChildren().addAll(headerLabel, summaryLabel, buttonGrid);

        // สร้าง Scene และส่งกลับ (ขนาดหน้าจอ 800x600)
        return new Scene(layout, 800, 600);
    }

    // เมธอดสำหรับอัปเดตข้อความใน summaryLabel
    public void updateSummary(double totalExpenses, double monthlyBudget) {
        double remainingBudget = monthlyBudget - totalExpenses;
        summaryLabel.setText(String.format(
            "ยอดรวมรายจ่าย: %.2f บาท\nงบประมาณรายเดือน: %.2f บาท\nคงเหลือ: %.2f บาท",
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

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16px; " +
                "-fx-background-color:rgb(113, 189, 113); " + // สีเขียวอ่อน (Pale Green)
                "-fx-text-fill:rgb(22, 22, 22); " + // ตัวอักษรสีขาว
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px;");
        return button;
    }
}