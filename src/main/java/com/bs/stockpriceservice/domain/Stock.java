package com.bs.stockpriceservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class Stock {
    private String stockCode;
    private String stockName ;
    private String stockPrice;
    private String stockVolume;
    private String stockHighestPrice;
    private String stockLowestPrice;
    private String stockPrdyVrssSign;

    @Builder
    public Stock(String stockCode, String stockName, String stockPrice, String stockVolume, String stockHighestPrice, String stockLowestPrice, String stockPrdyVrssSign) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stockVolume = stockVolume;
        this.stockHighestPrice = stockHighestPrice;
        this.stockLowestPrice = stockLowestPrice;
        this.stockPrdyVrssSign = stockPrdyVrssSign;
    }
}
