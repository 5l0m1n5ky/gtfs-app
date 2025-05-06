package com.gtfsapp.api.service.kafka;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class KafkaProducerService {

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, Object message) {
        kafkaTemplate.send(topic, message)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info("Message sent to topic [{}]: {}", topic, message);
                    } else {
                        log.error("Failed to send message to topic [{}]", topic, exception);
                    }
                });
    }
}
