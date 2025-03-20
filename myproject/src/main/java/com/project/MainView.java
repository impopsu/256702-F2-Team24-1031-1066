package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainView {

    private Controller controller;

    public MainView(Controller controller) {
        this.controller = controller;
    }

    public Scene createMainScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        Button addExpenseButton = new Button("เพิ่มค่าใช้จ่าย");
        addExpenseButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addExpenseButton.setOnAction(e -> {
            controller.showAddExpenseView();
        });

        Button viewExpensesButton = new Button("ดูรายการค่าใช้จ่าย");
        viewExpensesButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        viewExpensesButton.setOnAction(e -> {
            controller.showViewExpensesView();
        });

        Button addCategoryButton = new Button("เพิ่มหมวดหมู่ใหม่");
        addCategoryButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF9800; -fx-text-fill: white;");
        addCategoryButton.setOnAction(e -> {
            controller.showAddCategoryView();
        });

        Button deleteCategoryButton = new Button("ลบหมวดหมู่");
        deleteCategoryButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF9800; -fx-text-fill: white;");
        deleteCategoryButton.setOnAction(e -> {
            showDeleteCategoryDialog();
        });

        Button searchExpensesButton = new Button("ค้นหารายการค่าใช้จ่าย");
        searchExpensesButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF9800; -fx-text-fill: white;");
        searchExpensesButton.setOnAction(e -> {
            controller.showSearchExpensesView();
        });

        Button profileButton = new Button("โปรไฟล์ผู้ใช้");
        profileButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF9800; -fx-text-fill: white;");
        profileButton.setOnAction(e -> {
            controller.showUserProfileView();
        });

        Button logoutButton = new Button("ออกจากระบบ");
        logoutButton.setStyle("-fx-font-size: 16px; -fx-background-color: #f44336; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> {
            controller.logout();
        });

        VBox mainLayout = new VBox(20, addExpenseButton, viewExpensesButton, addCategoryButton, deleteCategoryButton, searchExpensesButton, profileButton, logoutButton);
        mainLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(mainLayout);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(mainLayout);
        borderPane.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(borderPane, 1024, 768); // ปรับขนาดหน้าจอเป็น 1024x768
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
}