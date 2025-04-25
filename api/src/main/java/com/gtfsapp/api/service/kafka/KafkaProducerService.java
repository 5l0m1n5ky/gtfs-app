package com.gtfsapp.api.service.kafka;

import com.gtfsapp.api.service.interfaces.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService<T> implements KafkaProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Value("${kafka.topic.default:default-topic}")
    private final String defaultTopic;

    public KafkaProducerService(KafkaTemplate<String, T> kafkaTemplate,  String defaultTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.defaultTopic = defaultTopic;
    }

    @Override
    public void send(String topic, T message) {
        String targetTopic = topic != null ? topic : defaultTopic;

        kafkaTemplate.send(targetTopic, message)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info("Message sent to topic [{}]: {}", targetTopic, message);
                    } else {
                        log.error("Failed to send message to topic [{}]: {}", targetTopic, message, exception);
                    }
                });
    }
}

