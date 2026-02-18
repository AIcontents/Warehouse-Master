package com.warehouse.monitor;

import com.warehouse.service.Warehouse;
import com.warehouse.strategy.StockEventStrategy;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WarehouseMonitor {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Warehouse warehouse;
    private final StockEventStrategy strategy;

    public WarehouseMonitor(Warehouse warehouse, StockEventStrategy strategy) {
        this.warehouse = warehouse;
        this.strategy = strategy;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> strategy.execute(warehouse), 0, 30, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}
