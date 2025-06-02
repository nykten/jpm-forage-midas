package com.jpmc.midascore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.foundation.Transaction;

@Component
public class KafkaConsumer {
    @Value("${general.kafka-topic}")
    private String topic;

    // logger instance
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

//    @KafkaListener(topics = "#KafkaListener.topic", groupId = "midas-group")
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {
        log.info("Received transaction: {}", transaction);
    }
}
