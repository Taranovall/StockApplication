package com.stocks.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class StockRequest {

    private long stockId;
    @Min(value = 1, message = "Amount must be greater than 1")
    @Max(value = 999, message = "Amount must be less than 999")
    private int amountOfStocks;
    private String operation;
}
