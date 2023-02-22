package com.stocks.util;

public class Constant {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String META_DATA = "Meta Data";
    public static final String LAST_REFRESHED = "3. Last Refreshed";
    public static final String TIME_SERIES_5_MIN = "Time Series (5min)";
    public static final String CLOSE = "4. close";

    public static final String INSERT_OR_ELSE_UPDATE_STOCK =
            """
                    INSERT INTO Stock (name, price, symbol)
                    values (:#{#s.name}, :#{#s.price}, :#{#s.symbol})
                    ON CONFLICT (symbol, name) DO UPDATE
                    SET price = :#{#s.price}
                    """;

    public static final String NOT_ENOUGH_MONEY_EXCEPTION = "You don't have enough money on your bank account";
    public static final String NOT_ENOUGH_STOCK_AMOUNT_EXCEPTION = "You don't have such amount of stocks";
    public static final String OPERATION_DOES_NOT_EXIST_EXCEPTION = "Operation '%s' doesn't exist";


    public static final String BUY = "buy";
    public static final String SELL = "sell";

    public static final String USER_ID_ERROR = "Incorrect user id";
}
