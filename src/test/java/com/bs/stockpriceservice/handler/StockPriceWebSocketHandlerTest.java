package com.bs.stockpriceservice.handler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@WebMvcTest
class StockPriceWebSocketHandlerTest {

    @MockBean
    private StockPriceWebSocketHandler stockPriceWebSocketHandler;
    @Test
    void sendStockMessageTest(){
        //given
        String stock = "1";
        //when, then
        stockPriceWebSocketHandler.sendStockPrices(stock);
    }

}