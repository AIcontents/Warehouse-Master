package com.warehouse.service;

import com.warehouse.model.AbstractProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class Warehouse {
    private static final Warehouse INSTANCE = new Warehouse();

    private final Map<Integer, AbstractProduct> products = new ConcurrentHashMap<>();

    private Warehouse() {
    }

    public static Warehouse getInstance() {
        return INSTANCE;
    }

    public void addProduct(AbstractProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (products.putIfAbsent(product.getId(), product) != null) {
            throw new IllegalArgumentException("Product with id " + product.getId() + " already exists");
        }
    }

    public Optional<AbstractProduct> getProductById(int id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<AbstractProduct> getAllProducts() {
        return products.values().stream()
                .sorted(Comparator.comparingInt(AbstractProduct::getId))
                .toList();
    }

    public boolean removeProduct(int id) {
        return products.remove(id) != null;
    }

    public void clear() {
        products.clear();
    }

    public List<AbstractProduct> searchByName(String namePart) {
        String query = namePart == null ? "" : namePart.toLowerCase();
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(query))
                .sorted(Comparator.comparing(AbstractProduct::getName))
                .toList();
    }

    public List<AbstractProduct> filterByMinPrice(BigDecimal minPrice) {
        BigDecimal threshold = minPrice == null ? BigDecimal.ZERO : minPrice;
        return products.values().stream()
                .filter(product -> product.getPrice().compareTo(threshold) >= 0)
                .sorted(Comparator.comparing(AbstractProduct::getPrice))
                .toList();
    }

    public <T extends AbstractProduct> List<T> getProductsByType(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (AbstractProduct product : products.values()) {
            if (type.isInstance(product)) {
                result.add(type.cast(product));
            }
        }
        return result;
    }

    public int getTotalQuantity() {
        return products.values().stream()
                .mapToInt(AbstractProduct::getQuantity)
                .sum();
    }
}
