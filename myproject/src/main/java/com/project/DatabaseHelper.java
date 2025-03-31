package com.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseHelper {

    private static List<Expense> expenses = new ArrayList<>();
    private static int nextId = 1;
    private static List<User> users = new ArrayList<>();
    private static List<ExpenseCategory> categories = new ArrayList<>();
    private static final String DATABASE_URL = "jdbc:sqlite:mydatabase.db"; // เปลี่ยนชื่อไฟล์ฐานข้อมูลตามที่คุณต้องการ

    static {
        users.add(new User("user", "password", "User", "Name", "user@example.com", "1234567890"));
        users.add(new User("user", "password", "User", "Name", "user@example.com", "1234567890"));
        categories.add(new ExpenseCategory("ค่าอาหาร"));
        categories.add(new ExpenseCategory("ค่าที่พัก"));
        categories.add(new ExpenseCategory("ค่าเดินทาง"));
    }

    // สร้างตารางฐานข้อมูลหากยังไม่มี
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "username TEXT NOT NULL," +
                     "password TEXT NOT NULL," +
                     "monthly_budget REAL DEFAULT 0.0" +
                     ");";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ตรวจสอบการเข้าสู่ระบบ
    public static boolean checkLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // ดึงข้อมูลผู้ใช้ตามชื่อผู้ใช้
    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // ลงทะเบียนผู้ใช้ใหม่
    public static boolean registerUser(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        users.add(new User(username, password, firstName, lastName, email, phoneNumber));
        return true;
    }

    // เพิ่มหมวดหมู่ค่าใช้จ่ายใหม่
    public static void addCategory(String name) {
        categories.add(new ExpenseCategory(name));
    }

    // ลบหมวดหมู่ค่าใช้จ่าย
    public static void deleteCategory(String name) {
        categories.removeIf(category -> category.getName().equals(name));
    }

    // แก้ไขหมวดหมู่ค่าใช้จ่าย
    public static void editCategory(String oldName, String newName) {
        for (ExpenseCategory category : categories) {
            if (category.getName().equals(oldName)) {
                category.setName(newName);
                break;
            }
        }
    }

    // ดึงหมวดหมู่ค่าใช้จ่ายทั้งหมด
    public static List<ExpenseCategory> getAllCategories() {
        return new ArrayList<>(categories);
    }

    // เพิ่มรายการค่าใช้จ่ายใหม่
    public static void addExpense(String description, double amount, LocalDate date) {
        expenses.add(new Expense(nextId++, description, amount, date));
    }

    public static void addExpense(String description, double amount, LocalDate date, String category) {
        expenses.add(new Expense(nextId++, description, amount, date, category)); // เพิ่มหมวดหมู่
    }

    // ดึงรายการค่าใช้จ่ายทั้งหมด
    public static List<Expense> getAllExpenses() {
        List<Expense> expensesList = new ArrayList<>();
        for (Expense expense : expenses) {
            expensesList.add(new Expense(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                expense.getCategory() // เพิ่มหมวดหมู่
            ));
        }
        return expensesList;
    }

    // ลบรายการค่าใช้จ่ายตาม ID
    public static boolean deleteExpense(int id) {
        return expenses.removeIf(expense -> expense.getId() == id);
    }

    // แก้ไขรายการค่าใช้จ่ายตาม ID
    public static boolean editExpense(int id, String newDescription, double newAmount, LocalDate newDate) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expense.setDescription(newDescription);
                expense.setAmount(newAmount);
                expense.setDate(newDate);
                return true;
            }
        }
        return false;
    }

    // ดึงรายการค่าใช้จ่ายทั้งหมดในรูปแบบ String
    public static List<String> getAllExpensesList() {
        List<String> expenseStrings = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseStrings.add(expense.getId() + " - " + expense.getDescription() + " - " + expense.getAmount() + " - " + expense.getDate());
        }
        return expenseStrings;
    }

    // ดึงรายการผู้ใช้ทั้งหมด
    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

    // อัปเดตข้อมูลผู้ใช้
    public static void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                break;
            }
        }
    }

    public static double getTotalExpensesForCurrentMonth() {
        String sql = "SELECT SUM(amount) FROM expenses WHERE strftime('%Y-%m', date) = strftime('%Y-%m', 'now')";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1); // คืนค่าผลรวมของค่าใช้จ่าย
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // คืนค่า 0.0 หากไม่มีข้อมูล
    }

    public static void updateUserBudget(int userId, double newBudget) {
        String sql = "UPDATE users SET monthly_budget = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBudget);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            System.out.println("เชื่อมต่อฐานข้อมูลสำเร็จ!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}