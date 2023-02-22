package com.stocks.service;

import com.stocks.entity.Stock;

import java.util.List;

public interface StockService {

    List<Stock> findAll();

    /**
     * Inserts all stocks into the table if they do not exist,
     * otherwise updates the price of each stock
     * @param stocks
     */
    void insertOrUpdateStocks(List<Stock> stocks);

    Stock findById(Long id);
}
