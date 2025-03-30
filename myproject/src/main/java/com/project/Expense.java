package com.project;

import java.time.LocalDate;
import javafx.beans.property.*;

public class Expense {
    private final IntegerProperty id;
    private final StringProperty description;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty category; // เพิ่มฟิลด์ category

    public Expense(int id, String description, double amount, LocalDate date, String category) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.category = new SimpleStringProperty(category); // กำหนดค่า category
    }

    public Expense(int id, String description, double amount, LocalDate date) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.category = new SimpleStringProperty(""); // หมวดหมู่เริ่มต้นเป็นค่าว่าง
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }
}
