package com.bs.stockpriceservice.config;

import com.bs.stockpriceservice.handler.StockPriceWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;



@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private  final StockPriceWebSocketHandler stockPriceWebSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(stockPriceWebSocketHandler, "stock").setAllowedOriginPatterns("*");
    }
}
