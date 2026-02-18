package com.warehouse.persistence;

import com.warehouse.model.AbstractProduct;
import com.warehouse.model.ElectronicsProduct;
import com.warehouse.model.FoodProduct;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WarehouseFileRepository {

    public void save(Path file, List<AbstractProduct> products) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("type,id,name,price,quantity,extra");
            writer.newLine();
            for (AbstractProduct product : products) {
                writer.write(serialize(product));
                writer.newLine();
            }
        }
    }

    public List<AbstractProduct> load(Path file) throws IOException {
        List<AbstractProduct> products = new ArrayList<>();
        if (!Files.exists(file)) {
            return products;
        }

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                products.add(deserialize(line));
            }
        }
        return products;
    }

    private String serialize(AbstractProduct product) {
        if (product instanceof FoodProduct food) {
            return String.join(",",
                    food.getType(),
                    String.valueOf(food.getId()),
                    food.getName(),
                    food.getPrice().toPlainString(),
                    String.valueOf(food.getQuantity()),
                    food.getExpirationDate().toString());
        }

        if (product instanceof ElectronicsProduct electronics) {
            return String.join(",",
                    electronics.getType(),
                    String.valueOf(electronics.getId()),
                    electronics.getName(),
                    electronics.getPrice().toPlainString(),
                    String.valueOf(electronics.getQuantity()),
                    String.valueOf(electronics.getWarrantyMonths()));
        }

        throw new IllegalArgumentException("Unsupported product type: " + product.getClass());
    }

    private AbstractProduct deserialize(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 6) {
            throw new IllegalArgumentException("Invalid record: " + line);
        }

        String type = tokens[0];
        int id = Integer.parseInt(tokens[1]);
        String name = tokens[2];
        BigDecimal price = new BigDecimal(tokens[3]);
        int quantity = Integer.parseInt(tokens[4]);

        return switch (type) {
            case "FOOD" -> new FoodProduct(id, name, price, quantity, LocalDate.parse(tokens[5]));
            case "ELECTRONICS" -> new ElectronicsProduct(id, name, price, quantity, Integer.parseInt(tokens[5]));
            default -> throw new IllegalArgumentException("Unknown product type: " + type);
        };
    }
}
