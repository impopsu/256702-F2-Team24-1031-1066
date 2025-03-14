package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;

public class ViewExpensesView {

    private Controller controller;

    public ViewExpensesView(Controller controller) {
        this.controller = controller;
    }

    public Scene createViewExpensesScene() {
        Label title = new Label("ดูรายการทั้งหมด");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ดึงรายการจากฐานข้อมูลและแสดงใน ListView
        ListView<String> expenseListView = new ListView<>();
        List<String> expenses = DatabaseHelper.getAllExpensesList();  // ฟังก์ชันใหม่ที่จะแปลงข้อมูลเป็น List
        expenseListView.getItems().addAll(expenses);

        Button deleteButton = new Button("ลบรายจ่ายที่เลือก");
        deleteButton.setOnAction(e -> {
            String selectedExpense = expenseListView.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                // ดึงชื่อรายการจาก string เพื่อใช้ในการลบ
                String expenseName = selectedExpense.split(" - ")[0]; // สมมุติว่า format "ชื่อ - จำนวนเงิน"
                DatabaseHelper.deleteExpense(expenseName);
                expenseListView.getItems().remove(selectedExpense);
            }
        });

        Button backButton = new Button("ย้อนกลับ");
        backButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(10, title, expenseListView, deleteButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px;");

        return new Scene(layout, 400, 300);
    }
}
