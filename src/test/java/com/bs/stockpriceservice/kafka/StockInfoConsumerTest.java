package com.bs.stockpriceservice.kafka;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(properties = "spring.profiles.active=test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = "stock", brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class StockInfoConsumerTest {
    @Autowired
    private StockInfoConsumer stockInfoConsumer;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_NAME = "stock";

    @Test
    void stockInfoConsumerTest() throws InterruptedException {
        //given
        String message = "stock";
        //when
        kafkaTemplate.send(TOPIC_NAME, message);
        kafkaTemplate.flush();

        Thread.sleep(1000);

        String payload = stockInfoConsumer.getPayload();
        //then
        Assertions.assertEquals(message, payload);
    }
}