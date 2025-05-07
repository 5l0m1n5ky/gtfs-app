package com.gtfsapp.api.service.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtfsapp.api.model.dto.VehicleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final SimpMessagingTemplate wsTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.vehicles}")
    public void onRawKafkaMessage(String rawJson) {
        log.info("Received raw JSON from Kafka");
        try {
            List<VehicleDto> vehicles = objectMapper.readValue(rawJson, new TypeReference<>() {
            });
            log.info("Parsed {} vehicles", vehicles.size());
            wsTemplate.convertAndSend("/topic/vehicles", vehicles);
        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
        }
    }
}
