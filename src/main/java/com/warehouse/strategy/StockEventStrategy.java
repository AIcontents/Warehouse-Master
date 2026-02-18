package com.warehouse.strategy;

import com.warehouse.service.Warehouse;

public interface StockEventStrategy {
    void execute(Warehouse warehouse);
}
