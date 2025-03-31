package com.project;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Expense {
    private final IntegerProperty id;
    private final StringProperty description;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty category;

    // คอนสตรัคเตอร์ที่รองรับพารามิเตอร์ทั้งหมด
    public Expense(int id, String description, double amount, LocalDate date, String category) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.category = new SimpleStringProperty(category);
    }

    // Getter และ Setter (ตามที่ได้เพิ่มไว้ก่อนหน้านี้)
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty categoryProperty() {
        return category;
    }
}
