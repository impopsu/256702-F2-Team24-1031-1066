package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView {

    private Controller controller;

    public MainView(Controller controller) {
        this.controller = controller;
    }

    public Scene createMainScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        Button addExpenseButton = new Button("เพิ่มค่าใช้จ่าย");
        addExpenseButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addExpenseButton.setOnAction(e -> {
            controller.showAddExpenseView();
        });

        Button viewExpensesButton = new Button("ดูรายการค่าใช้จ่าย");
        viewExpensesButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
        viewExpensesButton.setOnAction(e -> {
            controller.showViewExpensesView();
        });

        Button logoutButton = new Button("ออกจากระบบ");
        logoutButton.setStyle("-fx-font-size: 16px; -fx-background-color: #f44336; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> {
            controller.logout();
        });

        // สร้างไอคอนโปรไฟล์ผู้ใช้
        ImageView profileIcon = new ImageView(new Image("file:profile_icon.png")); // ใช้รูปภาพที่คุณต้องการ
        profileIcon.setFitWidth(50);
        profileIcon.setFitHeight(50);
        profileIcon.setStyle("-fx-background-radius: 25px; -fx-border-radius: 25px; -fx-border-color: #FF9800; -fx-border-width: 2px;");
        profileIcon.setOnMouseClicked(e -> {
            controller.showUserProfileView();
        });

        HBox topLayout = new HBox();
        topLayout.setAlignment(Pos.TOP_RIGHT);
        topLayout.getChildren().add(profileIcon);

        VBox mainLayout = new VBox(20, addExpenseButton, viewExpensesButton, logoutButton);
        mainLayout.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topLayout);
        borderPane.setCenter(mainLayout);
        borderPane.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(borderPane, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}
