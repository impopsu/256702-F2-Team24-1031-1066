package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class UserProfileView {

    private Controller controller;

    public UserProfileView(Controller controller) {
        this.controller = controller;
    }

    public Scene createUserProfileScene(User user) {
        Label titleLabel = new Label("โปรไฟล์ผู้ใช้");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label usernameLabel = new Label("ชื่อผู้ใช้:");
        TextField usernameField = new TextField(user.getUsername());
        usernameField.setPromptText("ชื่อผู้ใช้");
        usernameField.setDisable(true);

        Label firstNameLabel = new Label("ชื่อจริง:");
        TextField firstNameField = new TextField(user.getFirstName());
        firstNameField.setPromptText("ชื่อจริง");

        Label lastNameLabel = new Label("นามสกุล:");
        TextField lastNameField = new TextField(user.getLastName());
        lastNameField.setPromptText("นามสกุล");

        Label emailLabel = new Label("อีเมล:");
        TextField emailField = new TextField(user.getEmail());
        emailField.setPromptText("อีเมล");

        Label phoneLabel = new Label("เบอร์โทรศัพท์:");
        TextField phoneField = new TextField(user.getPhoneNumber());
        phoneField.setPromptText("เบอร์โทรศัพท์");
        phoneField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d") || phoneField.getText().length() >= 10) {
                event.consume();
            }
        });

        Label budgetLabel = new Label("งบประมาณรายเดือน:");
        TextField budgetField = new TextField(String.valueOf(user.getMonthlyBudget()));
        budgetField.setPromptText("งบประมาณรายเดือน");

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            try {
                double budget = Double.parseDouble(budgetField.getText());
                user.setMonthlyBudget(budget);
                user.setFirstName(firstNameField.getText());
                user.setLastName(lastNameField.getText());
                user.setEmail(emailField.getText());
                user.setPhoneNumber(phoneField.getText());
                controller.updateUserProfile(user);
                showAlert("สำเร็จ", "บันทึกงบประมาณเรียบร้อยแล้ว");
            } catch (NumberFormatException ex) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกงบประมาณเป็นตัวเลข");
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, usernameLabel, usernameField, firstNameLabel, firstNameField, lastNameLabel, lastNameField, emailLabel, emailField, phoneLabel, phoneField, budgetLabel, budgetField, saveButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 1024, 768); // ปรับขนาดหน้าจอเป็น 1024x768
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}