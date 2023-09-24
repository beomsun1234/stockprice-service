package com.bs.stockpriceservice.kafka;



import com.bs.stockpriceservice.kafka.dto.KafkaResponseStockDto;
import com.bs.stockpriceservice.domain.Stock;
import com.bs.stockpriceservice.handler.StockPriceWebSocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockInfoConsumer {

    private final static Map<String,String> stockName = Map.ofEntries(
            Map.entry("329180","현대중공업"),
            Map.entry( "005930","삼성전자"),
            Map.entry("035720", "카카오"),
            Map.entry("373220", "LG에너지솔루션"),
            Map.entry("207940", "삼성바이오로직스"),
            Map.entry("051910", "LG화학"),
            Map.entry("035420", "네이버"),
            Map.entry("012330", "현대모비스"),
            Map.entry("005380", "현대자동차"),
            Map.entry("105560", "KB금융"),
            Map.entry("086790", "하나금융지주"),
            Map.entry("055550", "신한지주"),
            Map.entry("323410", "카카오뱅크"),
            Map.entry("000270", "기아자동차"),
            Map.entry("005490", "POSCO"),
            Map.entry("032830", "삼성생명"),
            Map.entry("024110", "기업은행"),
            Map.entry("377300", "카카오페이"),
            Map.entry("316140", "우리금융지주"),
            Map.entry("352820", "하이브")
            );
    private final StockPriceWebSocketHandler stockPriceWebSocketHandler;
    private final ObjectMapper objectMapper;
    private String payload;

    @KafkaListener(topics = "stock", groupId = "stock-service")
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("----------------------------------------------------------------------------------------------------------------");
        log.info(record.value(), "\n");

        String message = convertStockListToPayload(convertMessageToStockList(record.value()));

        log.info(message);
        log.info("----------------------------------------------------------------------------------------------------------------");
        setPayload(message);
        stockPriceWebSocketHandler.sendStockPrices(message);
    }
    private List<Stock> convertMessageToStockList(String value) throws JsonProcessingException {
        KafkaResponseStockDto[] resStocks = objectMapper.readValue(value, KafkaResponseStockDto[].class);
        return Arrays.stream(resStocks).parallel().map(
                it -> Stock.builder()
                        .stockVolume(it.getStockVolume())
                        .stockHighestPrice(it.getStockHighestPrice())
                        .stockPrice(it.getStockPrice())
                        .stockLowestPrice(it.getStockLowestPrice())
                        .stockCode(it.getStockCode())
                        .stockName(stockName.get(it.getStockCode()))
                        .stockPrdyVrssSign(it.getStockPrdyVrssSign())
                        .build()
        ).sorted(
                Comparator.comparing(
                        it-> it.getStockCode()
                )
        ).collect(Collectors.toList());
    }
    private String convertStockListToPayload(List<Stock> stocks) throws JsonProcessingException {
        return objectMapper.writeValueAsString(stocks);
    }
    public String getPayload(){
        return this.payload;
    }

    public void setPayload(String payload){
        this.payload= payload;
    }
}
