package com.warehouse.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FoodProduct extends AbstractProduct {
    private LocalDate expirationDate;

    public FoodProduct(int id, String name, BigDecimal price, int quantity, LocalDate expirationDate) {
        super(id, name, price, quantity);
        setExpirationDate(expirationDate);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }

    @Override
    public String getType() {
        return "FOOD";
    }

    @Override
    public String toString() {
        return "FoodProduct{" + super.toString() + ", expirationDate=" + expirationDate + '}';
    }
}
