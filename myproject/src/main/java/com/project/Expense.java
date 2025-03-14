package com.project;

public class Expense {
    private int id;
    private String description;
    private double amount;
    private String category;

    // Constructor
    public Expense(int id, String description, double amount, String category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getter for category
    public String getCategory() {
        return category;
    }

    // Setter for category
    public void setCategory(String category) {
        this.category = category;
    }
}
