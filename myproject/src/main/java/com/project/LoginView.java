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
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");

        Button loginButton = new Button("เข้าสู่ระบบ");
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
        signupButton.setOnAction(e -> {
            controller.showSignUpView();
        });

        VBox layout = new VBox(15, title, usernameField, passwordField, loginButton, signupButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        return new Scene(layout, 400, 300);
    }
}
