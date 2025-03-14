package com.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Controller {

    private Stage stage;
    private MainView mainView;
    private AddExpenseView addExpenseView;
    private ViewExpensesView viewExpensesView;
    private LoginView loginView;
    private SignUpView signUpView;
    private UserProfileView userProfileView;
    private AddCategoryView addCategoryView;
    private ManageCategoriesView manageCategoriesView;
    private EditExpenseView editExpenseView;
    private SearchExpensesView searchExpensesView;
    private boolean isLoggedIn = false;
    private User currentUser;

    public Controller(Stage stage) {
        this.stage = stage;
        this.mainView = new MainView(this);
        this.addExpenseView = new AddExpenseView(this);
        this.viewExpensesView = new ViewExpensesView(this);
        this.loginView = new LoginView(this);
        this.signUpView = new SignUpView(this);
        this.userProfileView = new UserProfileView(this);
        this.addCategoryView = new AddCategoryView(this);
        this.manageCategoriesView = new ManageCategoriesView(this);
        this.editExpenseView = new EditExpenseView(this);
        this.searchExpensesView = new SearchExpensesView(this);
    }

    public void start() {
        if (DatabaseHelper.getUsers().isEmpty()) {
            showSignUpView();
        } else {
            showLoginView();
        }
    }

    public void showMainView() {
        if (isLoggedIn) {
            stage.setScene(mainView.createMainScene());
        }
    }

    public void showAddExpenseView() {
        stage.setScene(addExpenseView.createAddExpenseScene());
    }

    public void showViewExpensesView() {
        stage.setScene(viewExpensesView.createViewExpensesScene());
    }

    public void showSignUpView() {
        stage.setScene(signUpView.createSignUpScene());
    }

    public void showLoginView() {
        stage.setScene(loginView.createLoginScene());
        stage.setTitle("Expense Tracker");
        stage.setWidth(1024); // ปรับขนาดหน้าจอเป็น 1024x768
        stage.setHeight(768);
        stage.show();
    }

    public void showUserProfileView() {
        if (currentUser != null) {
            stage.setScene(userProfileView.createUserProfileScene(currentUser));
            stage.setWidth(1024); // ปรับขนาดหน้าจอเป็น 1024x768
            stage.setHeight(768);
        }
    }

    public void showAddCategoryView() {
        stage.setScene(addCategoryView.createAddCategoryScene());
    }

    public void showManageCategoriesView() {
        stage.setScene(manageCategoriesView.createManageCategoriesScene());
    }

    public void showEditExpenseView(int expenseId, String currentDescription, double currentAmount, String currentCategory, LocalDate currentDate, String currentType) {
        stage.setScene(editExpenseView.createEditExpenseScene(expenseId, currentDescription, currentAmount, currentCategory, currentDate, currentType));
    }

    public void showSearchExpensesView() {
        stage.setScene(searchExpensesView.createSearchExpensesScene());
    }

    public void addExpense(String description, double amount, String category, LocalDate date, String type) {
        DatabaseHelper.addExpense(description, amount, category, date, type);
        showViewExpensesView(); // เพิ่มการแสดงหน้าดูรายการค่าใช้จ่ายหลังจากบันทึก
    }

    public boolean login(String username, String password) {
        boolean result = DatabaseHelper.checkLogin(username, password);
        if (result) {
            isLoggedIn = true;
            for (User user : DatabaseHelper.getUsers()) {
                if (user.getUsername().equals(username)) {
                    currentUser = user;
                    break;
                }
            }
            showMainView();
        }
        return result;
    }

    public boolean register(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        return DatabaseHelper.registerUser(username, password, firstName, lastName, email, phoneNumber);
    }

    public void logout() {
        isLoggedIn = false;
        currentUser = null;
        start();
    }

    public boolean editExpense(int id, String description, double amount, String category, LocalDate date, String type) {
        boolean result = DatabaseHelper.editExpense(id, description, amount, category, date);
        showMainView();
        return result;
    }

    public void updateUserProfile(User user) {
        DatabaseHelper.updateUser(user);
        showMainView();
    }
}

public class AddExpenseView {
    private Controller controller;

    public AddExpenseView(Controller controller) {
        this.controller = controller;
    }

    public Scene createAddExpenseScene() {
        Label titleLabel = new Label("เพิ่มรายการ");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label typeLabel = new Label("ประเภท:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("รายรับ", "รายจ่าย");

        Label descriptionLabel = new Label("คำอธิบาย:");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("คำอธิบาย");

        Label amountLabel = new Label("จำนวนเงิน:");
        TextField amountField = new TextField();
        amountField.setPromptText("จำนวนเงิน");

        Label categoryLabel = new Label("หมวดหมู่:");
        ComboBox<String> categoryComboBox = new ComboBox<>();

        typeComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            if (selectedType.equals("รายรับ")) {
                categoryComboBox.getItems().setAll(DatabaseHelper.getIncomeCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
            } else if (selectedType.equals("รายจ่าย")) {
                categoryComboBox.getItems().setAll(DatabaseHelper.getExpenseCategories().stream().map(ExpenseCategory::getName).toArray(String[]::new));
            }
        });

        Label dateLabel = new Label("วันที่:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        Button saveButton = new Button("บันทึก");
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();
            String type = typeComboBox.getValue();
            controller.addExpense(description, amount, category, date, type);
        });

        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> controller.showMainView());

        VBox layout = new VBox(15, titleLabel, typeLabel, typeComboBox, descriptionLabel, descriptionField, amountLabel, amountField, categoryLabel, categoryComboBox, dateLabel, datePicker, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: #f0f0f0;");

        return new Scene(layout, 800, 600); // ปรับขนาดหน้าจอเป็น 800x600
    }
}
