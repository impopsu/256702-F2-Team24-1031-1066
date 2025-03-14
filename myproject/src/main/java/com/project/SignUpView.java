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
        Label title = new Label("สมัครสมาชิก");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");
        usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("ชื่อจริง");
        firstNameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("นามสกุล");
        lastNameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField emailField = new TextField();
        emailField.setPromptText("อีเมล");
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("เบอร์โทรศัพท์");
        phoneNumberField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button registerButton = new Button("สมัครสมาชิก");
        registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกข้อมูลให้ครบทุกช่อง");
            } else if (!phoneNumber.matches("\\d{10}")) {
                showAlert("ข้อผิดพลาด", "เบอร์โทรศัพท์ต้องเป็นตัวเลข 10 หลัก");
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

        VBox formLayout = new VBox(15, title, usernameField, passwordField, firstNameField, lastNameField, emailField, phoneNumberField);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        HBox buttonLayout = new HBox(15, backButton, registerButton);
        buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);
        buttonLayout.setStyle("-fx-padding: 30px;");

        BorderPane layout = new BorderPane();
        layout.setCenter(formLayout);
        layout.setBottom(buttonLayout);
        BorderPane.setAlignment(backButton, Pos.BOTTOM_LEFT);

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
