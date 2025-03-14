package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddCategoryView {

    private Controller controller;

    public AddCategoryView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddCategoryScene() {
        Label title = new Label("เพิ่มหมวดหมู่ใหม่");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField categoryField = new TextField();
        categoryField.setPromptText("ชื่อหมวดหมู่");
        categoryField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label typeLabel = new Label("ประเภท:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("รายรับ", "รายจ่าย");

        Button addButton = new Button("เพิ่มหมวดหมู่");
        addButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setOnAction(e -> {
            String categoryName = categoryField.getText();
            String categoryType = typeComboBox.getValue();
            if (!categoryName.isEmpty() && categoryType != null) {
                DatabaseHelper.addCategory(categoryName, categoryType.equals("รายรับ") ? "income" : "expense");
                controller.showMainView();
            } else {
                showAlert("ข้อผิดพลาด", "กรุณากรอกชื่อหมวดหมู่และเลือกประเภท");
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, title, categoryField, typeLabel, typeComboBox, addButton, backButton);
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