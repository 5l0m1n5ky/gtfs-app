package com.gtfsapp.api.service.mbta;

import com.gtfsapp.api.model.dto.AlertDto;
import com.gtfsapp.api.service.interfaces.DataProcessing;
import com.gtfsapp.api.service.kafka.KafkaProducerService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class AlertService implements DataProcessing<AlertDto> {

    private KafkaProducerService kafkaProducerService;
    private String alertTopic;

    @Autowired
    public void setKafkaProducerService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Value("${kafka.topic-alerts}")
    public void setAlertTopic(String alertTopic) {
        this.alertTopic = alertTopic;
    }

    @Override
    public void process(AlertDto dto) {
        log.info("Processing alert: {}", dto);
        kafkaProducerService.send(alertTopic, dto);
    }
}
