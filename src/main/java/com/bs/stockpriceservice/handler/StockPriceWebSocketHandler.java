package com.bs.stockpriceservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class StockPriceWebSocketHandler extends TextWebSocketHandler {
    private HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //todo
        log.info("message");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId());
    }

    public void sendStockPrices(String stockInfo){
        sessionMap.entrySet().stream().forEach(s -> {
            try {
                s.getValue().sendMessage(new TextMessage(stockInfo));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
