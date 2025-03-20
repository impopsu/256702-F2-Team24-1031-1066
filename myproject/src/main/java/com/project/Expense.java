package com.project;

import java.time.LocalDate;
import javafx.beans.property.*;

public class Expense {
    private final IntegerProperty id;
    private final StringProperty description;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;

    public Expense(int id, String description, double amount, LocalDate date) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleObjectProperty<>(date);
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

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}
