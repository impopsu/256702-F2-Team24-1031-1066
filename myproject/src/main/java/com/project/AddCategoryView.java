package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AddCategoryView {

    private Controller controller;

    public AddCategoryView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddCategoryScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #FAF3E0;");

        Label headerLabel = new Label("เพิ่มหมวดหมู่ใหม่");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill: #333333;");

        TextField categoryField = new TextField();
        categoryField.setPromptText("ชื่อหมวดหมู่");
        categoryField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button addButton = new Button("เพิ่มหมวดหมู่");
        addButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setOnAction(e -> {
            String categoryName = categoryField.getText();
            if (!categoryName.isEmpty()) {
                DatabaseHelper.addCategory(categoryName);
                controller.showMainView();
            } else {
                showAlert("ข้อผิดพลาด", "กรุณากรอกชื่อหมวดหมู่");
            }
        });

        Button backButton = new Button("⬅️ กลับ");
        backButton.setOnAction(e -> controller.showMainView()); // กลับไปหน้าหลัก

        layout.getChildren().addAll(headerLabel, categoryField, addButton, backButton);

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