package com.project;

import javafx.stage.Stage;

import java.time.LocalDate;

public class Controller {

    private Stage stage;
    private User currentUser;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        showLoginView();
    }

    public void showMainView() {
        MainView mainView = new MainView(this);
        stage.setScene(mainView.createMainScene());
        stage.show();
    }

    public void showAddExpenseView() {
        AddExpenseView addExpenseView = new AddExpenseView(this);
        stage.setScene(addExpenseView.createAddExpenseScene());
        stage.show();
    }

    public void showViewExpensesView() {
        ViewExpensesView viewExpensesView = new ViewExpensesView(this);
        stage.setScene(viewExpensesView.createViewExpensesScene());
        stage.show();
    }

    public void showEditExpenseView(int expenseId, String currentDescription, double currentAmount, LocalDate currentDate) {
        EditExpenseView editExpenseView = new EditExpenseView(this);
        stage.setScene(editExpenseView.createEditExpenseScene(expenseId, currentDescription, currentAmount, currentDate));
        stage.show();
    }

    public void addExpense(String description, double amount, LocalDate date) {
        DatabaseHelper.addExpense(description, amount, date);
    }

    public void editExpense(int expenseId, String newDescription, double newAmount, LocalDate newDate) {
        DatabaseHelper.editExpense(expenseId, newDescription, newAmount, newDate);
        showViewExpensesView();
    }

    public void showAddCategoryView() {
        AddCategoryView addCategoryView = new AddCategoryView(this);
        stage.setScene(addCategoryView.createAddCategoryScene());
        stage.show();
    }

    public void showSearchExpensesView() {
        SearchExpensesView searchExpensesView = new SearchExpensesView(this);
        stage.setScene(searchExpensesView.createSearchExpensesScene());
        stage.show();
    }

    public void showUserProfileView() {
        UserProfileView userProfileView = new UserProfileView(this);
        stage.setScene(userProfileView.createUserProfileScene(currentUser));
        stage.show();
    }

    public void updateUserProfile(User user) {
        DatabaseHelper.updateUser(user);
        showMainView();
    }

    public void logout() {
        currentUser = null;
        showLoginView();
    }

    public void showLoginView() {
        LoginView loginView = new LoginView(this);
        stage.setScene(loginView.createLoginScene());
        stage.show();
    }

    public void showSignUpView() {
        SignUpView signUpView = new SignUpView(this);
        stage.setScene(signUpView.createSignUpScene());
        stage.show();
    }

    public boolean login(String username, String password) {
        if (DatabaseHelper.checkLogin(username, password)) {
            currentUser = DatabaseHelper.getUserByUsername(username);
            return true;
        }
        return false;
    }

    public boolean register(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        return DatabaseHelper.registerUser(username, password, firstName, lastName, email, phoneNumber);
    }
}

