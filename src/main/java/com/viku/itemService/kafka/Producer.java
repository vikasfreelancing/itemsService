package com.viku.itemService.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message,String topic) {
        this.kafkaTemplate.send(topic, message);
        System.out.println("Sent sample message [" + message + "] to " + topic);
    }

}
