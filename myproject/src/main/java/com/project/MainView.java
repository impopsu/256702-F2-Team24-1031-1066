package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #1E3A8A;"); // พื้นหลังน้ำเงินเข้ม

        // แสดงยอดรวมรายรับ-รายจ่าย และคงเหลือ
        Label summaryLabel = new Label();
        summaryLabel.setFont(new Font("Arial", 18));
        summaryLabel.setStyle("-fx-text-fill: #FACC15;"); // สีเหลือง
        updateSummary(summaryLabel);

        // ปุ่มต่างๆ
        Button addExpenseButton = createStyledButton("เพิ่มค่าใช้จ่าย");
        addExpenseButton.setOnAction(e -> controller.showAddExpenseView());

        Button viewExpensesButton = createStyledButton("ดูรายการค่าใช้จ่าย");
        viewExpensesButton.setOnAction(e -> controller.showViewExpensesView());

        Button addCategoryButton = createStyledButton("เพิ่มหมวดหมู่ใหม่");
        addCategoryButton.setOnAction(e -> controller.showAddCategoryView());

        Button deleteCategoryButton = createStyledButton("ลบหมวดหมู่");
        deleteCategoryButton.setOnAction(e -> showDeleteCategoryDialog());

        Button searchExpensesButton = createStyledButton("ค้นหารายการค่าใช้จ่าย");
        searchExpensesButton.setOnAction(e -> controller.showSearchExpensesView());

        Button profileButton = createStyledButton("โปรไฟล์ผู้ใช้");
        profileButton.setOnAction(e -> controller.showUserProfileView());

        Button logoutButton = createStyledButton("ออกจากระบบ");
        logoutButton.setOnAction(e -> controller.logout());

        // จัดเรียงปุ่มใน GridPane
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(20); // ระยะห่างแนวนอนระหว่างปุ่ม
        buttonGrid.setVgap(20); // ระยะห่างแนวตั้งระหว่างปุ่ม
        buttonGrid.setAlignment(Pos.CENTER);

        buttonGrid.add(addExpenseButton, 0, 0);
        buttonGrid.add(viewExpensesButton, 1, 0);
        buttonGrid.add(addCategoryButton, 0, 1);
        buttonGrid.add(deleteCategoryButton, 1, 1);
        buttonGrid.add(searchExpensesButton, 0, 2);
        buttonGrid.add(profileButton, 1, 2);
        buttonGrid.add(logoutButton, 0, 3, 2, 1); // ปุ่ม Logout กินพื้นที่ 2 คอลัมน์

        // เพิ่ม Label และ GridPane ลงใน Layout หลัก
        layout.getChildren().addAll(summaryLabel, buttonGrid);

        // BorderPane สำหรับจัด Layout หลัก
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout);
        borderPane.setStyle("-fx-padding: 30px; -fx-background-color: #1E3A8A;"); // พื้นหลังน้ำเงินเข้ม

        return new Scene(borderPane, 1024, 768); // ขนาดหน้าจอ 1024x768
    }

    private void updateSummary(Label summaryLabel) {
        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double monthlyBudget = controller.getCurrentUser().getMonthlyBudget();
        double remainingBudget = monthlyBudget - totalExpenses;

        summaryLabel.setText(String.format(
                "ยอดรวมรายจ่าย: %.2f บาท\nงบประมาณรายเดือน: %.2f บาท\nคงเหลือ: %.2f บาท",
                totalExpenses, monthlyBudget, remainingBudget
        ));
    }

    private void showDeleteCategoryDialog() {
        ListView<String> categoryListView = new ListView<>();
        categoryListView.getItems().addAll(DatabaseHelper.getAllCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("ลบหมวดหมู่");
        alert.setHeaderText("เลือกหมวดหมู่ที่ต้องการลบ");
        alert.getDialogPane().setContent(categoryListView);

        alert.showAndWait().ifPresent(response -> {
            String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                DatabaseHelper.deleteCategory(selectedCategory);
                showAlert("สำเร็จ", "ลบหมวดหมู่เรียบร้อย!");
            } else {
                showAlert("ผิดพลาด", "กรุณาเลือกหมวดหมู่ที่ต้องการลบ");
            }
        });
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
        button.setStyle("-fx-font-size: 16px; " +
                "-fx-background-color: #FACC15; " + // สีเหลือง
                "-fx-text-fill: black; " + // ตัวอักษรสีดำ
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px;");
        return button;
    }
}