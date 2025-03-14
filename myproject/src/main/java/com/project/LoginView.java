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
        Label title = new Label("เข้าสู่ระบบ");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");
        usernameField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

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

        Button signupButton = new Button("สมัครสมาชิก");
        signupButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        signupButton.setOnAction(e -> {
            controller.showSignUpView();
        });

        VBox layout = new VBox(15, title, usernameField, passwordField, loginButton, signupButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 600, 400); // ปรับขนาดหน้าจอเป็น 600x400
    }
}
