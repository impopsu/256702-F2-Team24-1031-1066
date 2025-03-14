package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
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
        usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label passwordLabel = new Label("รหัสผ่าน:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button loginButton = new Button("เข้าสู่ระบบ");
        loginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (controller.login(username, password)) {
                controller.showMainView(); 
            } else {
                System.out.println("ข้อมูลไม่ถูกต้อง");
            }
        });

        Button signUpButton = new Button("สมัครสมาชิก");
        signUpButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        signUpButton.setOnAction(e -> controller.showSignUpView());

        VBox layout = new VBox(15, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, signUpButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}
