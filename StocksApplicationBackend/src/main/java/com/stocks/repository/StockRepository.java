package com.stocks.repository;

import com.stocks.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.stocks.util.Constant.INSERT_OR_ELSE_UPDATE_STOCK;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAll();

    Optional<Stock> findStockByName(String name);

    /**
     * Inserts a stock into the table if it doesn't exist, otherwise updates the stock price
     * @param stock
     */
    @Modifying
    @Query(value = INSERT_OR_ELSE_UPDATE_STOCK, nativeQuery = true)
    void insertStockOrElseUpdatePrice(@Param("s") Stock stock);
}
