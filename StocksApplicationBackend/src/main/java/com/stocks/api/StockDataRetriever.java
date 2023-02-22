package com.stocks.api;

import com.stocks.entity.Stock;
import com.stocks.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.stocks.util.Constant.*;

/**
 * Retrieve data from Alpha Vantage API
 */
@Configuration
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class StockDataRetriever {

    @Value("${api.base-url}")
    private String apiUrl;
    @Value("#{${stock.symbols}}")
    private Map<String, String> stocks;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final StockService stockService;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    /**
     * Executes every N minutes.
     * N = application.properties ${api.sleep.duration}
     */
    @Scheduled(fixedDelayString = "${api.sleep-duration}", timeUnit = TimeUnit.MINUTES)
    public void retrieveData() {
        List<Stock> stockList = stocks.entrySet().stream()
                .map(this::getStockFromApi)
                .collect(Collectors.toList());

        stockService.insertOrUpdateStocks(stockList);
    }

    /**
     * Get data from API
     * @param entry
     * @return object which contains all data retrieved from API
     */
    private Stock getStockFromApi(Map.Entry<String, String> entry) {
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl + entry.getValue(), String.class);
        try {
            double lastPrice = getLastPrice(response);
            return new Stock()
                    .setName(entry.getKey())
                    .setSymbol(entry.getValue())
                    .setPrice(lastPrice);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Get last price from API
     * @param response
     * @return price of the stock
     * @throws JsonProcessingException
     */
    private double getLastPrice(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
        String lastRefreshed = jsonResponse
                .get(META_DATA)
                .get(LAST_REFRESHED)
                .asText();

        String lastPrice = jsonResponse
                .get(TIME_SERIES_5_MIN)
                .get(lastRefreshed)
                .get(CLOSE)
                .asText();
        return Double.parseDouble(lastPrice);
    }
}
