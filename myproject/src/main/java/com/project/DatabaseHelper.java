package com.project;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:expenses.db";

    // เชื่อมต่อฐานข้อมูล
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();  // แสดงข้อผิดพลาดแบบละเอียด
        }
        return conn;
    }

    // สร้างตาราง expenses ถ้ายังไม่มี
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (id INTEGER PRIMARY KEY, name TEXT, amount REAL)";  // ปรับจาก TEXT เป็น REAL

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();  // แสดงข้อผิดพลาดแบบละเอียด
        }
    }

    // เพิ่มรายจ่ายลงในฐานข้อมูล
    public static void addExpense(String name, String amount) {
        String sql = "INSERT INTO expenses(name, amount) VALUES(?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, Double.parseDouble(amount));  // แปลง amount เป็น Double เพื่อความแม่นยำ
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // แสดงข้อผิดพลาดแบบละเอียด
        }
    }

    // ดึงรายการทั้งหมดจากฐานข้อมูล
    public static String getAllExpenses() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");  // ใช้ double เพื่อความแม่นยำ
                sb.append(name).append(" - ").append(amount).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // แสดงข้อผิดพลาดแบบละเอียด
        }
        return sb.toString();
    }
}
