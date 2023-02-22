package com.stocks.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockResponse {

    private String message;
    private double amountOfMoney;
}
