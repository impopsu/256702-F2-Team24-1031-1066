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
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");

        Button registerButton = new Button("สมัครสมาชิก");
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
        backButton.setOnAction(e -> {
            controller.start(); // กลับไปที่หน้า Login
        });

        VBox layout = new VBox(15, title, usernameField, passwordField, registerButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        return new Scene(layout, 400, 300);
    }
}
