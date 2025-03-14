package com.project;

import javafx.stage.Stage;

public class Controller {

    private Stage stage;
    private MainView mainView;
    private AddExpenseView addExpenseView;
    private ViewExpensesView viewExpensesView;
    private LoginView loginView;
    private SignUpView signUpView;
    private boolean isLoggedIn = false;

    public Controller(Stage stage) {
        this.stage = stage;
        this.mainView = new MainView(this);
        this.addExpenseView = new AddExpenseView(this);
        this.viewExpensesView = new ViewExpensesView(this);
        this.loginView = new LoginView(this);
        this.signUpView = new SignUpView(this);
    }

    public void start() {
        stage.setScene(loginView.createLoginScene());
        stage.setTitle("Expense Tracker");
        stage.show();
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

    public void addExpense(String name, double amount) {
        DatabaseHelper.addExpense(name, amount);
        showMainView();
    }

    public boolean login(String username, String password) {
        boolean result = DatabaseHelper.checkLogin(username, password);
        if (result) {
            isLoggedIn = true;
            showMainView();
        }
        return result;
    }

    public boolean register(String username, String password) {
        return DatabaseHelper.registerUser(username, password);
    }

    public void logout() {
        isLoggedIn = false;
        start();
    }

    public boolean editExpense(int id, String name, double amount) {
        boolean result = DatabaseHelper.editExpense(id, name, amount);
        showMainView();
        return result;
    }
}
