package com.gtfsapp.api.service.mbta;

import com.gtfsapp.api.model.dto.AlertDto;
import com.gtfsapp.api.service.interfaces.DataProcessing;
import com.gtfsapp.api.service.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService implements DataProcessing<AlertDto> {

    private final KafkaProducerService<AlertDto> kafkaProducerService;

    @Override
    public void process(AlertDto alertDto) {
        log.debug("Processing alert: {}", alertDto);
        kafkaProducerService.send(null, alertDto);
    }
}
