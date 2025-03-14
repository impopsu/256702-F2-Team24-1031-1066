package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SignUpView {

    private Controller controller;

    public SignUpView(Controller controller) {
        this.controller = controller;
    }

    public Scene createSignUpScene() {
        Label titleLabel = new Label("สมัครสมาชิก");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label usernameLabel = new Label("ชื่อผู้ใช้:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");
        usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label passwordLabel = new Label("รหัสผ่าน:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label firstNameLabel = new Label("ชื่อจริง:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("ชื่อจริง");
        firstNameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label lastNameLabel = new Label("นามสกุล:");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("นามสกุล");
        lastNameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label emailLabel = new Label("อีเมล:");
        TextField emailField = new TextField();
        emailField.setPromptText("อีเมล");
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label phoneNumberLabel = new Label("เบอร์โทรศัพท์:");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("เบอร์โทรศัพท์");
        phoneNumberField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button signUpButton = new Button("สมัครสมาชิก");
        signUpButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกข้อมูลให้ครบทุกช่อง");
            } else if (!phoneNumber.matches("\\d{10}")) {
                showAlert("ข้อผิดพลาด", "เบอร์โทรศัพท์ต้องเป็นตัวเลข");
            } else {
                if (controller.register(username, password, firstName, lastName, email, phoneNumber)) {
                    showAlert("สำเร็จ", "สมัครสมาชิกสำเร็จ");
                    controller.start(); // กลับไปที่หน้า Login
                } else {
                    showAlert("ข้อผิดพลาด", "ชื่อผู้ใช้ซ้ำ");
                }
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            controller.showLoginView(); // กลับไปที่หน้า Login
        });

        VBox layout = new VBox(15, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, firstNameLabel, firstNameField, lastNameLabel, lastNameField, emailLabel, emailField, phoneNumberLabel, phoneNumberField, signUpButton, backButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
