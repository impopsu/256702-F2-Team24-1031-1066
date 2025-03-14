package com.project;

import javafx.stage.Stage;

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
        stage.setWidth(800); // ปรับขนาดหน้าจอเป็น 800x600
        stage.setHeight(600);
        stage.show();
    }

    public void showUserProfileView() {
        if (currentUser != null) {
            stage.setScene(userProfileView.createUserProfileScene(currentUser));
            stage.setWidth(800); // ปรับขนาดหน้าจอเป็น 800x600
            stage.setHeight(600);
        }
    }

    public void showAddCategoryView() {
        stage.setScene(addCategoryView.createAddCategoryScene());
    }

    public void showManageCategoriesView() {
        stage.setScene(manageCategoriesView.createManageCategoriesScene());
    }

    public void showEditExpenseView(int expenseId, String currentDescription, double currentAmount, String currentCategory) {
        stage.setScene(editExpenseView.createEditExpenseScene(expenseId, currentDescription, currentAmount, currentCategory));
    }

    public void addExpense(String description, double amount, String category) {
        DatabaseHelper.addExpense(description, amount, category);
        showMainView();
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

    public boolean editExpense(int id, String description, double amount, String category) {
        boolean result = DatabaseHelper.editExpense(id, description, amount, category);
        showMainView();
        return result;
    }
}
