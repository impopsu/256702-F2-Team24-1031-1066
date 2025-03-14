package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class ManageCategoriesView {

    private Controller controller;

    public ManageCategoriesView(Controller controller) {
        this.controller = controller;
    }

    public Scene createManageCategoriesScene() {
        Label title = new Label("จัดการหมวดหมู่");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        ListView<String> categoryListView = new ListView<>();
        List<ExpenseCategory> categories = DatabaseHelper.getAllCategories();
        for (ExpenseCategory category : categories) {
            categoryListView.getItems().add(category.getName());
        }
        categoryListView.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // ปุ่มลบหมวดหมู่
        Button deleteButton = new Button("ลบหมวดหมู่ที่เลือก");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                DatabaseHelper.deleteCategory(selectedCategory);
                categoryListView.getItems().remove(selectedCategory);
                showAlert("สำเร็จ", "ลบหมวดหมู่เรียบร้อย!");
            } else {
                showAlert("ผิดพลาด", "กรุณาเลือกหมวดหมู่ที่ต้องการลบ");
            }
        });

        // ปุ่มแก้ไขหมวดหมู่
        Button editButton = new Button("แก้ไขหมวดหมู่");
        editButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                TextInputDialog dialog = new TextInputDialog(selectedCategory);
                dialog.setTitle("แก้ไขหมวดหมู่");
                dialog.setHeaderText("กรุณากรอกชื่อหมวดหมู่ใหม่");
                Optional<String> newCategoryName = dialog.showAndWait();
                if (newCategoryName.isPresent()) {
                    DatabaseHelper.editCategory(selectedCategory, newCategoryName.get());
                    categoryListView.getItems().set(categoryListView.getSelectionModel().getSelectedIndex(), newCategoryName.get());
                    showAlert("สำเร็จ", "แก้ไขหมวดหมู่เรียบร้อย!");
                }
            } else {
                showAlert("ผิดพลาด", "กรุณาเลือกหมวดหมู่ที่ต้องการแก้ไข");
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, title, categoryListView, deleteButton, editButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}