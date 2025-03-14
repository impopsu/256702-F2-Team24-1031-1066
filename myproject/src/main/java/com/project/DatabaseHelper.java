package com.project;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static List<Expense> expenses = new ArrayList<>();
    private static int nextId = 1;

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("user", "password"));
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
    public static boolean registerUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        users.add(new User(username, password));
        return true;
    }

    // สร้างตารางฐานข้อมูลใหม่
    public static void createTable() {
        expenses.clear();
        nextId = 1;
        System.out.println("Database initialized.");
    }

    // เพิ่มรายการค่าใช้จ่ายใหม่
    public static void addExpense(String description, double amount) {
        expenses.add(new Expense(nextId++, description, amount));
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
    public static boolean editExpense(int id, String newDescription, double newAmount) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expense.setDescription(newDescription);
                expense.setAmount(newAmount);
                return true;
            }
        }
        return false;
    }

    // ดึงรายการค่าใช้จ่ายทั้งหมดในรูปแบบ String
    public static List<String> getAllExpensesList() {
        List<String> expenseStrings = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseStrings.add(expense.getId() + " - " + expense.getDescription() + " - " + expense.getAmount());
        }
        return expenseStrings;
    }
}
