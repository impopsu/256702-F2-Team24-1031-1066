package com.project;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static List<Expense> expenses = new ArrayList<>();
    private static int nextId = 1; // Auto-increment ID

    public static void createTable() {
        // ในกรณีของ List ในหน่วยความจำ ไม่ต้องสร้างตารางจริง แต่เราสามารถใช้ method นี้เพื่อเตรียมข้อมูล
        expenses.clear();
        nextId = 1;
        System.out.println("Database initialized.");
    }

    public static void addExpense(String description, double amount) {
        expenses.add(new Expense(nextId++, description, amount));
    }

    public static List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public static boolean deleteExpense(int id) {
        return expenses.removeIf(expense -> expense.getId() == id);
    }

    // ✅ ฟังก์ชันแก้ไขข้อมูลในฐานข้อมูล
    public static boolean editExpense(int id, String newDescription, double newAmount) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expense.setDescription(newDescription);
                expense.setAmount(newAmount);
                return true; // สำเร็จ
            }
        }
        return false; // ไม่พบ ID
    }

    // ✅ ฟังก์ชันใหม่เพื่อแปลงรายการเป็น List<String> สำหรับแสดงใน UI
    public static List<String> getAllExpensesList() {
        List<String> expenseStrings = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseStrings.add(expense.getId() + " - " + expense.getDescription() + " - " + expense.getAmount());
        }
        return expenseStrings;
    }
}
