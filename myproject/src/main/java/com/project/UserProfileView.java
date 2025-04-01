package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UserProfileView {

    private Controller controller;
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    public UserProfileView(Controller controller) {
        this.controller = controller;
    }

    public Scene createUserProfileScene(User user) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #001F54;"); // พื้นหลังสีน้ำเงินเข้ม (กรมท่า)
        layout.setPrefSize(800, 600); // กำหนดขนาดคงที่

        Label headerLabel = new Label("โปรไฟล์ผู้ใช้");
        headerLabel.setFont(new Font("Arial", 24));
        headerLabel.setStyle("-fx-text-fill: #FFD700;"); // สีข้อความเป็นสีเหลืองเข้ม (Golden Yellow)

        Label usernameLabel = new Label("ชื่อผู้ใช้:");
        usernameLabel.setStyle("-fx-text-fill: #FFD700;"); // สีข้อความเป็นสีเหลืองเข้ม
        TextField usernameField = new TextField(user.getUsername());
        usernameField.setPromptText("ชื่อผู้ใช้");
        usernameField.setDisable(true);

        Label firstNameLabel = new Label("ชื่อจริง:");
        firstNameLabel.setStyle("-fx-text-fill: #FFD700;"); // สีข้อความเป็นสีเหลืองเข้ม
        TextField firstNameField = new TextField(user.getFirstName());
        firstNameField.setPromptText("ชื่อจริง");

        Label lastNameLabel = new Label("นามสกุล:");
        lastNameLabel.setStyle("-fx-text-fill: #FFD700;"); // สีข้อความเป็นสีเหลืองเข้ม
        TextField lastNameField = new TextField(user.getLastName());
        lastNameField.setPromptText("นามสกุล");

        Label emailLabel = new Label("อีเมล:");
        emailLabel.setStyle("-fx-text-fill: #FFD700;"); // สีข้อความเป็นสีเหลืองเข้ม
        TextField emailField = new TextField(user.getEmail());
        emailField.setPromptText("อีเมล");

        Label phoneLabel = new Label("เบอร์โทรศัพท์:");
        phoneLabel.setStyle("-fx-text-fill: #FFD700;"); // สีข้อความเป็นสีเหลืองเข้ม
        TextField phoneField = new TextField(user.getPhoneNumber());
        phoneField.setPromptText("เบอร์โทรศัพท์");
        phoneField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d") || phoneField.getText().length() >= 10) {
                event.consume();
            }
        });

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #FFD700; -fx-text-fill: #001F54;"); // ปุ่มสีเหลืองเข้ม
        saveButton.setOnAction(e -> {
            try {
                user.setFirstName(firstNameField.getText());
                user.setLastName(lastNameField.getText());
                user.setEmail(emailField.getText());
                user.setPhoneNumber(phoneField.getText());
                controller.updateUserProfile(user);
                showAlert("สำเร็จ", "บันทึกข้อมูลเรียบร้อยแล้ว");
            } catch (NumberFormatException ex) {
                showAlert("ข้อผิดพลาด", "กรุณากรอกข้อมูลให้ถูกต้อง");
            }
        });

        Button backButton = new Button("⬅️ กลับ");
        backButton.setStyle("-fx-font-size: 14px; -fx-background-color: #FFD700; -fx-text-fill: #001F54;"); // ปุ่มสีเหลืองเข้ม
        backButton.setOnAction(e -> controller.showMainView()); // กลับไปหน้าหลัก

        layout.getChildren().addAll(
            headerLabel, usernameLabel, usernameField,
            firstNameLabel, firstNameField,
            lastNameLabel, lastNameField,
            emailLabel, emailField,
            phoneLabel, phoneField,
            saveButton, backButton
        );

        return new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}