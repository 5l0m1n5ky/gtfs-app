package com.gtfsapp.api.service.interfaces;

public interface KafkaProducer<T> {
    void send(String topic, T message);
}
