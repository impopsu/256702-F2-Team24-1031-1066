package com.project;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.time.LocalDate;
import java.util.List;

public class Controller {

    private Stage stage;
    private User currentUser;
    private MainView mainView;
    private BudgetView budgetView;

    public Controller(Stage stage) {
        this.stage = stage;
        this.mainView = new MainView(this);
        this.budgetView = new BudgetView(this);
        stage.setResizable(false); // ป้องกันการปรับขนาดหน้าต่าง
    }

    public void start() {
        showLoginView();
    }

    public void showMainView() {
        Scene scene = mainView.createMainScene();
        stage.setScene(scene);
        stage.show();
        stage.getScene().getRoot().requestFocus(); // รีเซ็ต Focus
    }

    public void showAddExpenseView() {
        System.out.println("แสดงหน้าจอเพิ่มค่าใช้จ่าย"); // Debugging
        AddExpenseView addExpenseView = new AddExpenseView(this);
        stage.setScene(addExpenseView.createAddExpenseScene());
        stage.show();
    }

    public void showViewExpensesView() {
        System.out.println("แสดงหน้าจอดูรายการค่าใช้จ่าย"); // Debugging
        ViewExpensesView viewExpensesView = new ViewExpensesView(this);
        stage.setScene(viewExpensesView.createViewExpensesScene());
        stage.show();
    }


    public void showEditExpenseView(Expense expense) {
        EditExpenseView editExpenseView = new EditExpenseView(this, expense);
        stage.setScene(editExpenseView.createEditExpenseScene());
    }

    public void addExpense(String description, double amount, LocalDate date, String category) {
        DatabaseHelper.addExpense(description, amount, date, category);
        showViewExpensesView(); // แสดงหน้าดูรายการหลังจากเพิ่มข้อมูล
    }

    public void editExpense(int expenseId, String newDescription, double newAmount, LocalDate newDate) {
        DatabaseHelper.editExpense(expenseId, newDescription, newAmount, newDate);
        showViewExpensesView();
    }

    public void showAddCategoryView() {
        System.out.println("แสดงหน้าจอเพิ่มหมวดหมู่ใหม่"); // Debugging
        AddCategoryView addCategoryView = new AddCategoryView(this);
        stage.setScene(addCategoryView.createAddCategoryScene());
        stage.show();
    }

    public void showSearchExpensesView() {
        System.out.println("แสดงหน้าจอค้นหารายการค่าใช้จ่าย"); // Debugging
        SearchExpensesView searchExpensesView = new SearchExpensesView(this);
        stage.setScene(searchExpensesView.createSearchExpensesScene());
        stage.show();
    }

    public void showUserProfileView() {
        UserProfileView userProfileView = new UserProfileView(this);
        Scene scene = userProfileView.createUserProfileScene(currentUser);
        stage.setScene(scene);
        stage.show();
    }

    public void showBudgetView() {
        Scene scene = budgetView.createBudgetScene(); // เรียก BudgetView เพื่อสร้าง Scene
        stage.setScene(scene); // เปลี่ยน Scene บน Stage
        stage.show(); // แสดง Stage
    }

    public void updateUserProfile(User user) {
        DatabaseHelper.updateUser(user);
        showMainView();
    }

    public void logout() {
        System.out.println("ออกจากระบบ"); // Debugging
        showLoginView(); // กลับไปหน้าจอ Login
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

    public void checkBudget() {
        if (currentUser != null) {
            double totalExpenses = DatabaseHelper.getTotalExpensesForCurrentMonth();
            double budget = currentUser.getMonthlyBudget();

            if (budget > 0 && totalExpenses > budget) {
                showAlert("แจ้งเตือน", "คุณใช้จ่ายเกินงบประมาณที่ตั้งไว้แล้ว!");
            }
        }
    }

    public void updateBudget(double newBudget) {
        if (currentUser != null) {
            currentUser.setMonthlyBudget(newBudget); // อัปเดตในหน่วยความจำ
            DatabaseHelper.updateUserBudget(currentUser.getId(), newBudget); // อัปเดตในฐานข้อมูล

            // ดึงยอดรวมรายจ่ายใหม่
            double totalExpenses = DatabaseHelper.getTotalExpensesForCurrentMonth();

            showAlert("สำเร็จ", "งบประมาณถูกอัปเดตเรียบร้อยแล้ว");
        } else {
            showAlert("ข้อผิดพลาด", "ไม่สามารถอัปเดตงบประมาณได้");
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public User getCurrentUser() {
        return currentUser;
    }
    
    public void updateSummary(double totalExpenses, double monthlyBudget) {
        double remainingBudget = monthlyBudget - totalExpenses;
        System.out.println("ยอดรวมรายจ่าย: " + totalExpenses);
        System.out.println("งบประมาณ: " + monthlyBudget);
        System.out.println("คงเหลือ: " + remainingBudget);

    }

    public void showSummaryView() {
        SummaryView summaryView = new SummaryView(this);
        stage.setScene(summaryView.createSummaryScene());
        stage.show();
    }

    public void checkBudgetExceeded() {
        // ดึงข้อมูลยอดรวมค่าใช้จ่าย
        List<Expense> expenses = DatabaseHelper.getAllExpenses();
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();

        // ดึงงบประมาณของผู้ใช้
        double budget = getCurrentUser().getMonthlyBudget();

        // ตรวจสอบว่ามียอดใช้จ่ายเกินงบประมาณหรือไม่
        if (totalExpense > budget) {
            showAlert("แจ้งเตือน", "คุณใช้จ่ายเกินงบประมาณที่ตั้งไว้แล้ว!");
        }
    }
}
