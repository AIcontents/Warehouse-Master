package com.warehouse.model;

import java.math.BigDecimal;

public class ElectronicsProduct extends AbstractProduct {
    private int warrantyMonths;

    public ElectronicsProduct(int id, String name, BigDecimal price, int quantity, int warrantyMonths) {
        super(id, name, price, quantity);
        setWarrantyMonths(warrantyMonths);
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative");
        }
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getType() {
        return "ELECTRONICS";
    }

    @Override
    public String toString() {
        return "ElectronicsProduct{" + super.toString() + ", warrantyMonths=" + warrantyMonths + '}';
    }
}
