package com.stocks.controller;

import com.stocks.entity.Account;
import com.stocks.entity.Stock;
import com.stocks.entity.User;
import com.stocks.exception.IncorrectInputException;
import com.stocks.exception.NotEnoughMoneyException;
import com.stocks.payload.request.StockRequest;
import com.stocks.payload.response.StockResponse;
import com.stocks.service.AccountService;
import com.stocks.service.StockService;
import com.stocks.util.Constant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final AccountService accountService;

    @GetMapping("/stock")
    public List<Stock> getStocks() {
        return stockService.findAll();
    }

    @PutMapping("/stock")
    public ResponseEntity<StockResponse> buyStocks(@RequestBody @Valid StockRequest stockRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IncorrectInputException(bindingResult.getFieldError().getDefaultMessage());

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Account account = user.getAccount();
        Stock stock = stockService.findById(stockRequest.getStockId());
        double cost = stock.getPrice() * stockRequest.getAmountOfStocks();

        String result = executeOperation(stockRequest, account, stock, cost);

        return ResponseEntity.ok(new StockResponse(result, account.getAmountOfMoney()));
    }

    /**
     * Executed operation: selling or buying stock depends on request
     *
     * @return information about executing operation
     */
    private String executeOperation(StockRequest stockRequest, Account account, Stock stock, double cost) {
        String message = "";
        switch (stockRequest.getOperation()) {
            case Constant.BUY:
                message = buyStocks(stockRequest.getAmountOfStocks(), account, stock, cost);
                break;
            case Constant.SELL:
                message = sellStocks(stockRequest.getAmountOfStocks(), account, stock, cost);
                break;
            default:
                throw new IncorrectInputException(String.format(Constant.OPERATION_DOES_NOT_EXIST_EXCEPTION, stockRequest.getOperation()));
        }
        accountService.updateAccount(account);
        return message;
    }

    /**
     * @return information about buying stock
     */
    private String buyStocks(Integer amountOfStocks, Account account, Stock stock, double cost) {
        if ((account.getAmountOfMoney() - cost) < 0) {
            throw new NotEnoughMoneyException(Constant.NOT_ENOUGH_MONEY_EXCEPTION);
        }

        account.getStocks().merge(stock, amountOfStocks, Integer::sum);
        account.setAmountOfMoney(account.getAmountOfMoney() - cost);

        return String.format("You successfully purchased %s [%d]",
                stock.getSymbol(),
                amountOfStocks);
    }

    /**
     * @return information about selling stock
     */
    private String sellStocks(Integer amountOfStocks, Account account, Stock stock, double cost) {
        Integer availableStockAmount = account.getStocks().get(stock);
        if (availableStockAmount < amountOfStocks) {
            throw new IncorrectInputException(Constant.NOT_ENOUGH_STOCK_AMOUNT_EXCEPTION);
        }

        account.getStocks().merge(stock, -amountOfStocks, Integer::sum);
        account.setAmountOfMoney(account.getAmountOfMoney() + cost);

        return String.format("You successfully sold %s [%d]",
                stock.getSymbol(),
                amountOfStocks);
    }
}