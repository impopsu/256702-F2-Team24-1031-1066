package com.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static List<Expense> expenses = new ArrayList<>();
    private static int nextId = 1;
    private static List<User> users = new ArrayList<>();
    private static List<ExpenseCategory> categories = new ArrayList<>();

    static {
        users.add(new User("user", "password", "User", "Name", "user@example.com", "1234567890"));
        categories.add(new ExpenseCategory("ค่าอาหาร", "expense"));
        categories.add(new ExpenseCategory("ค่าที่พัก", "expense"));
        categories.add(new ExpenseCategory("ค่าเดินทาง", "expense"));
        categories.add(new ExpenseCategory("เงินเดือน", "income"));
        categories.add(new ExpenseCategory("โบนัส", "income"));
    }

    // สร้างตารางฐานข้อมูลหากยังไม่มี
    public static void createTable() {
        // ในตัวอย่างนี้ เราจะใช้ List แทนฐานข้อมูลจริง
        // คุณสามารถเพิ่มโค้ดสำหรับการสร้างตารางในฐานข้อมูลจริงได้ที่นี่
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
    public static void addCategory(String name, String type) {
        categories.add(new ExpenseCategory(name, type));
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

    // ดึงหมวดหมู่รายรับ
    public static List<ExpenseCategory> getIncomeCategories() {
        List<ExpenseCategory> incomeCategories = new ArrayList<>();
        for (ExpenseCategory category : categories) {
            if (category.getType().equals("income")) {
                incomeCategories.add(category);
            }
        }
        return incomeCategories;
    }

    // ดึงหมวดหมู่รายจ่าย
    public static List<ExpenseCategory> getExpenseCategories() {
        List<ExpenseCategory> expenseCategories = new ArrayList<>();
        for (ExpenseCategory category : categories) {
            if (category.getType().equals("expense")) {
                expenseCategories.add(category);
            }
        }
        return expenseCategories;
    }

    // เพิ่มรายการค่าใช้จ่ายใหม่
    public static void addExpense(String description, double amount, String category, LocalDate date, String type) {
        expenses.add(new Expense(nextId++, description, amount, category, date, type));
    }

    // ดึงรายการค่าใช้จ่ายทั้งหมด
    public static List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    // ลบรายการค่าใช้จ่ายตาม ID
    public static boolean deleteExpense(int id) {
        return expenses.removeIf(expense -> expense.getId() == id);
    }

    // แก้ไขรายการค่าใช้จ่ายตาม ID
    public static boolean editExpense(int id, String newDescription, double newAmount, String newCategory, LocalDate newDate) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expense.setDescription(newDescription);
                expense.setAmount(newAmount);
                expense.setCategory(newCategory);
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
            expenseStrings.add(expense.getId() + " - " + expense.getDescription() + " - " + expense.getAmount() + " - " + expense.getCategory() + " - " + expense.getDate() + " - " + (expense.getType().equals("income") ? "รายรับ" : "รายจ่าย"));
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
}