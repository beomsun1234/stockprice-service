package com.bs.stockpriceservice.kafka;



import com.bs.stockpriceservice.handler.StockPriceWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockInfoConsumer {

    private final StockPriceWebSocketHandler stockPriceWebSocketHandler;

    public StockInfoConsumer(StockPriceWebSocketHandler stockPriceWebSocketHandler){
        this.stockPriceWebSocketHandler = stockPriceWebSocketHandler;
    }

    @KafkaListener(topics = "stock", groupId = "stockprice-service")
    public void listen(String message) {
        log.info(message, "\n");
        stockPriceWebSocketHandler.sendStockPrices(message);
    }
}
