package com.warehouse.strategy;

import com.warehouse.model.FoodProduct;
import com.warehouse.service.Warehouse;

import java.time.LocalDate;
import java.util.List;

public class ExpirationAlertStrategy implements StockEventStrategy {
    @Override
    public void execute(Warehouse warehouse) {
        List<FoodProduct> expiringSoon = warehouse.getProductsByType(FoodProduct.class).stream()
                .filter(product -> !product.getExpirationDate().isBefore(LocalDate.now()))
                .filter(product -> !product.getExpirationDate().isAfter(LocalDate.now().plusDays(2)))
                .toList();

        if (!expiringSoon.isEmpty()) {
            System.out.println("[Monitor] Attention! Products expiring within 2 days:");
            expiringSoon.forEach(System.out::println);
        }
    }
}
