package com.warehouse.service;

import com.warehouse.model.ElectronicsProduct;
import com.warehouse.model.FoodProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.getInstance();
        warehouse.clear();
    }

    @Test
    void shouldAddProductAndFindById() {
        FoodProduct milk = new FoodProduct(1, "Milk", new BigDecimal("2.50"), 10, LocalDate.now().plusDays(3));

        warehouse.addProduct(milk);

        assertTrue(warehouse.getProductById(1).isPresent());
        assertEquals("Milk", warehouse.getProductById(1).orElseThrow().getName());
    }

    @Test
    void shouldThrowWhenAddingProductWithDuplicateId() {
        warehouse.addProduct(new ElectronicsProduct(2, "Laptop", new BigDecimal("1200.00"), 5, 24));

        assertThrows(IllegalArgumentException.class,
                () -> warehouse.addProduct(new ElectronicsProduct(2, "Phone", new BigDecimal("900.00"), 7, 12)));
    }

    @Test
    void shouldSearchProductsByNameUsingStreams() {
        warehouse.addProduct(new ElectronicsProduct(3, "Gaming Mouse", new BigDecimal("40.00"), 12, 12));
        warehouse.addProduct(new ElectronicsProduct(4, "Office Mouse", new BigDecimal("20.00"), 20, 6));
        warehouse.addProduct(new ElectronicsProduct(5, "Keyboard", new BigDecimal("70.00"), 8, 12));

        assertEquals(2, warehouse.searchByName("mouse").size());
    }

    @Test
    void shouldCalculateTotalQuantity() {
        warehouse.addProduct(new FoodProduct(6, "Cheese", new BigDecimal("5.50"), 4, LocalDate.now().plusDays(7)));
        warehouse.addProduct(new ElectronicsProduct(7, "Monitor", new BigDecimal("300.00"), 3, 24));

        assertEquals(7, warehouse.getTotalQuantity());
    }

    @Test
    void shouldRemoveProductById() {
        warehouse.addProduct(new FoodProduct(8, "Bread", new BigDecimal("1.20"), 25, LocalDate.now().plusDays(1)));

        assertTrue(warehouse.removeProduct(8));
        assertFalse(warehouse.getProductById(8).isPresent());
    }
}
