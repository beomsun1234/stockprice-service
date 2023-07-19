package com.bs.stockpriceservice.kafka;



import com.bs.stockpriceservice.handler.StockPriceWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockInfoConsumer {

    private final StockPriceWebSocketHandler stockPriceWebSocketHandler;
    private String payload;
    public StockInfoConsumer(StockPriceWebSocketHandler stockPriceWebSocketHandler){
        this.stockPriceWebSocketHandler = stockPriceWebSocketHandler;
    }

    @KafkaListener(topics = "stock", groupId = "stock-service")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("----------------------------------------------------------------------------------------------------------------");
        log.info(record.value(), "\n");
        log.info("----------------------------------------------------------------------------------------------------------------");
        setPayload(record.value());
        stockPriceWebSocketHandler.sendStockPrices(this.payload);
    }

    public String getPayload(){
        return this.payload;
    }

    public void setPayload(String payload){
        this.payload= payload;
    }
}
