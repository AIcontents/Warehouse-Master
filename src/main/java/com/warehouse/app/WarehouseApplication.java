package com.warehouse.app;

import com.warehouse.model.AbstractProduct;
import com.warehouse.model.ElectronicsProduct;
import com.warehouse.model.FoodProduct;
import com.warehouse.monitor.WarehouseMonitor;
import com.warehouse.persistence.WarehouseFileRepository;
import com.warehouse.service.Warehouse;
import com.warehouse.strategy.ExpirationAlertStrategy;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class WarehouseApplication {
    private static final Path DATA_FILE = Path.of("warehouse-data.csv");

    private final Warehouse warehouse = Warehouse.getInstance();
    private final WarehouseFileRepository repository = new WarehouseFileRepository();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new WarehouseApplication().run();
    }

    private void run() {
        loadData();

        WarehouseMonitor monitor = new WarehouseMonitor(warehouse, new ExpirationAlertStrategy());
        monitor.start();

        boolean running = true;
        while (running) {
            printMenu();
            int option = readInt("Choose option: ");
            switch (option) {
                case 1 -> addFoodProduct();
                case 2 -> addElectronicsProduct();
                case 3 -> showAllProducts();
                case 4 -> searchByName();
                case 5 -> filterByMinPrice();
                case 6 -> removeProduct();
                case 7 -> saveData();
                case 0 -> running = false;
                default -> System.out.println("Unknown menu option.");
            }
        }

        monitor.stop();
        saveData();
        System.out.println("Bye!");
    }

    private void printMenu() {
        System.out.println("\n===== Warehouse Master =====");
        System.out.println("1. Add food product");
        System.out.println("2. Add electronics product");
        System.out.println("3. Show all products");
        System.out.println("4. Search by name");
        System.out.println("5. Filter by minimum price");
        System.out.println("6. Remove product by id");
        System.out.println("7. Save data");
        System.out.println("0. Exit");
    }

    private void addFoodProduct() {
        try {
            int id = readInt("Id: ");
            String name = readString("Name: ");
            BigDecimal price = readBigDecimal("Price: ");
            int quantity = readInt("Quantity: ");
            LocalDate expirationDate = LocalDate.parse(readString("Expiration date (YYYY-MM-DD): "));

            warehouse.addProduct(new FoodProduct(id, name, price, quantity, expirationDate));
            System.out.println("Food product added.");
        } catch (RuntimeException ex) {
            System.out.println("Cannot add product: " + ex.getMessage());
        }
    }

    private void addElectronicsProduct() {
        try {
            int id = readInt("Id: ");
            String name = readString("Name: ");
            BigDecimal price = readBigDecimal("Price: ");
            int quantity = readInt("Quantity: ");
            int warranty = readInt("Warranty months: ");

            warehouse.addProduct(new ElectronicsProduct(id, name, price, quantity, warranty));
            System.out.println("Electronics product added.");
        } catch (RuntimeException ex) {
            System.out.println("Cannot add product: " + ex.getMessage());
        }
    }

    private void showAllProducts() {
        List<AbstractProduct> products = warehouse.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Warehouse is empty.");
            return;
        }

        products.forEach(System.out::println);
        System.out.println("Total quantity: " + warehouse.getTotalQuantity());
    }

    private void searchByName() {
        String query = readString("Name part: ");
        List<AbstractProduct> found = warehouse.searchByName(query);
        if (found.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        found.forEach(System.out::println);
    }

    private void filterByMinPrice() {
        BigDecimal minPrice = readBigDecimal("Minimum price: ");
        List<AbstractProduct> result = warehouse.filterByMinPrice(minPrice);
        if (result.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        result.forEach(System.out::println);
    }

    private void removeProduct() {
        int id = readInt("Product id to remove: ");
        boolean removed = warehouse.removeProduct(id);
        System.out.println(removed ? "Product removed." : "Product was not found.");
    }

    private void loadData() {
        try {
            List<AbstractProduct> products = repository.load(DATA_FILE);
            warehouse.clear();
            products.forEach(warehouse::addProduct);
            System.out.println("Loaded " + products.size() + " products from " + DATA_FILE);
        } catch (IOException | RuntimeException ex) {
            System.out.println("Cannot load data: " + ex.getMessage());
        }
    }

    private void saveData() {
        try {
            repository.save(DATA_FILE, warehouse.getAllProducts());
            System.out.println("Data saved to " + DATA_FILE);
        } catch (IOException ex) {
            System.out.println("Cannot save data: " + ex.getMessage());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    private BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine();
            try {
                return new BigDecimal(raw);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid decimal number, try again.");
            }
        }
    }

    private String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            if (!value.isBlank()) {
                return value;
            }
            System.out.println("Value cannot be empty, try again.");
        }
    }
}
