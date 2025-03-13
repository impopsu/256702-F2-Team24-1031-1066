package com.project;

import javafx.stage.Stage;

public class Controller {

    private Stage stage;
    private MainView mainView;
    private AddExpenseView addExpenseView;
    private ViewExpensesView viewExpensesView;

    public Controller(Stage stage) {
        this.stage = stage;
        this.mainView = new MainView(this);
        this.addExpenseView = new AddExpenseView(this);
        this.viewExpensesView = new ViewExpensesView(this);
    }

    public void start() {
        stage.setScene(mainView.createMainScene());
        stage.setTitle("Expense Tracker");
        stage.show();
    }

    public void showMainView() {
        stage.setScene(mainView.createMainScene());
    }

    public void showAddExpenseView() {
        stage.setScene(addExpenseView.createAddExpenseScene());
    }

    public void showViewExpensesView() {
        stage.setScene(viewExpensesView.createViewExpensesScene());
    }

    public void addExpense(String name, double amount) {
        DatabaseHelper.addExpense(name, amount);
        showMainView();
    }
}
