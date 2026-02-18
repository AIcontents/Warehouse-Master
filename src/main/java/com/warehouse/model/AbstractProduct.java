package com.warehouse.model;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class AbstractProduct {
    private final int id;
    private String name;
    private BigDecimal price;
    private int quantity;

    protected AbstractProduct(int id, String name, BigDecimal price, int quantity) {
        this.id = id;
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", quantity=" + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractProduct that = (AbstractProduct) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
