package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView {

    private Controller controller;

    public LoginView(Controller controller) {
        this.controller = controller;
    }

    public Scene createLoginScene() {
        Label titleLabel = new Label("เข้าสู่ระบบ");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label usernameLabel = new Label("ชื่อผู้ใช้:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");

        Label passwordLabel = new Label("รหัสผ่าน:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");

        Button loginButton = new Button("เข้าสู่ระบบ");
        loginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (controller.login(username, password)) {
                controller.showMainView();
            } else {
                showAlert("ข้อผิดพลาด", "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
            }
        });

        Button signUpButton = new Button("สมัครสมาชิก");
        signUpButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        signUpButton.setOnAction(e -> controller.showSignUpView());

        VBox layout = new VBox(15, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, signUpButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 1024, 768); // ปรับขนาดหน้าจอเป็น 1024x768
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
