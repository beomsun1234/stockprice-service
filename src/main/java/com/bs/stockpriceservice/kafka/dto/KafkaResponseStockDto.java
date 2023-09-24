package com.bs.stockpriceservice.kafka.dto;

import lombok.Data;

@Data
public class KafkaResponseStockDto {
    private String stockCode;
    private String stockPrice;
    private String stockVolume;
    private String stockHighestPrice;
    private String stockLowestPrice;
    private String stockPrdyVrssSign;
}
