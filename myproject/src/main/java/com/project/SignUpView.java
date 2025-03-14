package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

        Button registerButton = new Button("สมัครสมาชิก");
        registerButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (controller.register(username, password)) {
                System.out.println("สมัครสมาชิกสำเร็จ");
                controller.start(); // กลับไปที่หน้า Login
            } else {
                System.out.println("ชื่อผู้ใช้ซ้ำ");
            }
        });

        Button backButton = new Button("กลับไปยังหน้าล็อกอิน");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            controller.start(); // กลับไปที่หน้า Login
        });

        VBox layout = new VBox(15, title, usernameField, passwordField, registerButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 600, 400); // ปรับขนาดหน้าจอเป็น 600x400
    }
}
