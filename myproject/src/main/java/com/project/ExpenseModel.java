package com.project;

import java.sql.*;

public class ExpenseModel {

    private static final String URL = "jdbc:sqlite:expenses.db";

    // Connect to the database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Create the expenses table if it doesn't exist
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (id INTEGER PRIMARY KEY, name TEXT, amount REAL)";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new expense to the database
    public static void addExpense(String name, String amount) {
        String sql = "INSERT INTO expenses(name, amount) VALUES(?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, Double.parseDouble(amount));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all expenses from the database
    public static String getAllExpenses() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                sb.append(name).append(" - ").append(amount).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
