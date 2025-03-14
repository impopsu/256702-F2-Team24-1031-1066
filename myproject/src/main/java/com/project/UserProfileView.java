package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserProfileView {

    private Controller controller;

    public UserProfileView(Controller controller) {
        this.controller = controller;
    }

    public Scene createUserProfileScene(User user) {
        Label titleLabel = new Label("โปรไฟล์ผู้ใช้");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label usernameLabel = new Label("ชื่อผู้ใช้:");
        TextField usernameField = new TextField(user.getUsername());
        usernameField.setPromptText("ชื่อผู้ใช้");
        usernameField.setDisable(true);

        Label firstNameLabel = new Label("ชื่อจริง:");
        TextField firstNameField = new TextField(user.getFirstName());
        firstNameField.setPromptText("ชื่อจริง");

        Label lastNameLabel = new Label("นามสกุล:");
        TextField lastNameField = new TextField(user.getLastName());
        lastNameField.setPromptText("นามสกุล");

        Label emailLabel = new Label("อีเมล:");
        TextField emailField = new TextField(user.getEmail());
        emailField.setPromptText("อีเมล");

        Label phoneNumberLabel = new Label("เบอร์โทรศัพท์:");
        TextField phoneNumberField = new TextField(user.getPhoneNumber());
        phoneNumberField.setPromptText("เบอร์โทรศัพท์");

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setEmail(emailField.getText());
            user.setPhoneNumber(phoneNumberField.getText());
            controller.updateUserProfile(user);
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel,
                new HBox(10, usernameLabel, usernameField),
                new HBox(10, firstNameLabel, firstNameField),
                new HBox(10, lastNameLabel, lastNameField),
                new HBox(10, emailLabel, emailField),
                new HBox(10, phoneNumberLabel, phoneNumberField),
                saveButton, backButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}