package com.project;

import javafx.geometry.Insets;
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
        // Header
        Label titleLabel = new Label("เข้าสู่ระบบ");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFD700;"); // สีเหลืองเข้ม

        // Username
        Label usernameLabel = new Label("ชื่อผู้ใช้:");
        usernameLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        TextField usernameField = new TextField();
        usernameField.setPromptText("ชื่อผู้ใช้");
        usernameField.setPrefWidth(200); // กำหนดความกว้างเป็น 200 พิกเซล
        usernameField.setMaxWidth(200);
        usernameField.setMinWidth(200);

        // Password
        Label passwordLabel = new Label("รหัสผ่าน:");
        passwordLabel.setStyle("-fx-text-fill: #FFD700;"); // สีเหลืองเข้ม
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("รหัสผ่าน");
        passwordField.setPrefWidth(200); // กำหนดความกว้างเป็น 200 พิกเซล
        passwordField.setMaxWidth(200);
        passwordField.setMinWidth(200);

        // Login Button
        Button loginButton = new Button("เข้าสู่ระบบ");
        loginButton.setStyle("-fx-font-size: 14px; -fx-background-color: #FFD700; -fx-text-fill: #001F54;"); // ปุ่มสีเหลืองเข้ม
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (controller.login(username, password)) {
                controller.showMainView();
            } else {
                showAlert("ข้อผิดพลาด", "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
            }
        });

        // Sign Up Button
        Button signUpButton = new Button("สมัครสมาชิก");
        signUpButton.setStyle("-fx-font-size: 14px; -fx-background-color: #FFD700; -fx-text-fill: #001F54;"); // ปุ่มสีเหลืองเข้ม
        signUpButton.setOnAction(e -> controller.showSignUpView());

        // Layout
        VBox layout = new VBox(20, titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, signUpButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #001F54;"); // พื้นหลังสีน้ำเงินเข้ม (กรมท่า)

        // เพิ่ม Margin ให้กับปุ่ม
        VBox.setMargin(loginButton, new Insets(10, 0, 10, 0)); // เพิ่มระยะห่างบนและล่าง 10 พิกเซล
        VBox.setMargin(signUpButton, new Insets(10, 0, 10, 0)); // เพิ่มระยะห่างบนและล่าง 10 พิกเซล

        // Return Scene
        return new Scene(layout, 800, 600); // ขนาดหน้าจอ 800x600
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
