package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class UserProfileView {

    private Controller controller;

    public UserProfileView(Controller controller) {
        this.controller = controller;
    }

    public Scene createUserProfileScene(User user) {
        Label title = new Label("โปรไฟล์ผู้ใช้");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label usernameLabel = new Label("ชื่อผู้ใช้: " + user.getUsername());
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label firstNameLabel = new Label("ชื่อจริง: " + user.getFirstName());
        firstNameLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label lastNameLabel = new Label("นามสกุล: " + user.getLastName());
        lastNameLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label emailLabel = new Label("อีเมล: " + user.getEmail());
        emailLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label phoneNumberLabel = new Label("เบอร์โทรศัพท์: " + user.getPhoneNumber());
        phoneNumberLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, title, usernameLabel, firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}