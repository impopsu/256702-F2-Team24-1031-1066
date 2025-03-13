package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ViewExpensesView {

    private Controller controller;

    public ViewExpensesView(Controller controller) {
        this.controller = controller;
    }

    public Scene createViewExpensesScene() {
        Label title = new Label("ดูรายการทั้งหมด");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ดึงรายการจากฐานข้อมูลและแสดง
        Label expenseList = new Label(DatabaseHelper.getAllExpenses());

        Button backButton = new Button("ย้อนกลับ");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(10, title, expenseList, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        return new Scene(layout, 400, 300);
    }
}
