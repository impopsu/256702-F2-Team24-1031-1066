package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class SignUpView {

    private Controller controller;

    public SignUpView(Controller controller) {
        this.controller = controller;
    }

    public Scene createSignUpScene() {
        // Header
        Label titleLabel = new Label("สมัครสมาชิก");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFD700;"); // สีเหลืองเข้ม

        // Username
        Label usernameLabel = new Label("ชื่อผู้ใช้:");
        usernameLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");

        // Password
        Label passwordLabel = new Label("รหัสผ่าน:");
        passwordLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");

        // First Name
        Label firstNameLabel = new Label("ชื่อจริง:");
        firstNameLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("ชื่อจริง");

        // Last Name
        Label lastNameLabel = new Label("นามสกุล:");
        lastNameLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("นามสกุล");

        // Email
        Label emailLabel = new Label("อีเมล:");
        emailLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        TextField emailField = new TextField();
        emailField.setPromptText("อีเมล");

        // Phone Number
        Label phoneLabel = new Label("เบอร์โทรศัพท์:");
        phoneLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        TextField phoneField = new TextField();
        phoneField.setPromptText("เบอร์โทรศัพท์");
        phoneField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d") || phoneField.getText().length() >= 10) {
                event.consume();
            }
        });

        // Sign Up Button
        Button signUpButton = new Button("สมัครสมาชิก");
        signUpButton.setStyle("-fx-font-size: 14px; -fx-background-color: #FFD700; -fx-text-fill: #001F54;"); // ปุ่มสีเหลืองเข้ม
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneField.getText();

            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกข้อมูลให้ครบถ้วน");
                return;
            }

            if (controller.register(username, password, firstName, lastName, email, phoneNumber)) {
                showAlert("สำเร็จ", "สมัครสมาชิกเรียบร้อย!");
                controller.showLoginView();
            } else {
                showAlert("ข้อผิดพลาด", "ชื่อผู้ใช้นี้มีอยู่แล้ว");
            }
        });

        // Back Button
        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #FFD700; -fx-text-fill: #001F54;"); // ปุ่มสีเหลืองเข้ม
        backButton.setOnAction(e -> controller.showLoginView());

        // Layout
        VBox layout = new VBox(15, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, firstNameLabel, firstNameField, lastNameLabel, lastNameField, emailLabel, emailField, phoneLabel, phoneField, signUpButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #001F54;"); // พื้นหลังสีน้ำเงินเข้ม (กรมท่า)

        // Return Scene
        return new Scene(layout, 800, 600); // ขนาดหน้าจอ 800x600
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
