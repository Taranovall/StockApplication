package com.stocks.service.impl;

import com.stocks.entity.Stock;
import com.stocks.exception.StockNotFoundException;
import com.stocks.repository.StockRepository;
import com.stocks.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public void insertOrUpdateStocks(List<Stock> stocks) {
        stocks.forEach(stockRepository::insertStockOrElseUpdatePrice);
    }

    @Override
    @Transactional(readOnly = true)
    public Stock findById(Long id) {
        Optional<Stock> stock = stockRepository.findById(id);

        return stock.orElseThrow(() -> {
            String message = String.format("Stock with id '%d' doesn't exist", id);
            throw new StockNotFoundException(message);
        });
    }
}
