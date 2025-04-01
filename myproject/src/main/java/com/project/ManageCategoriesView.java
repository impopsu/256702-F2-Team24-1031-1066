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
        categoryListView.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // ดึงหมวดหมู่จากฐานข้อมูลและเพิ่มลงใน ListView
        List<ExpenseCategory> categories = DatabaseHelper.getAllCategories();
        System.out.println("หมวดหมู่ที่ดึงมา: " + categories); // Debugging
        for (ExpenseCategory category : categories) {
            categoryListView.getItems().add(category.getName());
        }

        // ปุ่มลบหมวดหมู่
        Button deleteButton = new Button("ลบหมวดหมู่ที่เลือก");
        deleteButton.setOnAction(e -> {
            String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
            if (selectedCategory == null) {
                showAlert("ข้อผิดพลาด", "กรุณาเลือกหมวดหมู่ที่ต้องการลบ");
                return;
            }

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("ยืนยันการลบ");
            confirmationAlert.setHeaderText("คุณต้องการลบหมวดหมู่ \"" + selectedCategory + "\" ใช่หรือไม่?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean success = DatabaseHelper.deleteCategory(selectedCategory);
                if (success) {
                    showAlert("สำเร็จ", "ลบหมวดหมู่ \"" + selectedCategory + "\" สำเร็จแล้ว");
                    categoryListView.getItems().remove(selectedCategory);
                } else {
                    showAlert("ข้อผิดพลาด", "ไม่สามารถลบหมวดหมู่ \"" + selectedCategory + "\" ได้ เนื่องจากมีการใช้งานอยู่");
                }
            }
        });

        VBox layout = new VBox(20, title, categoryListView, deleteButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #FAF3E0;");

        return new Scene(layout, 800, 600);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}