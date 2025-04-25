package com.gtfsapp.api.service.mbta;

import com.gtfsapp.api.model.dto.VehicleDto;
import com.gtfsapp.api.service.interfaces.DataProcessing;
import com.gtfsapp.api.service.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService implements DataProcessing<VehicleDto> {

    private final KafkaProducerService<VehicleDto> kafkaProducerService;

    @Override
    public void process(VehicleDto vehicleDto) {
        log.debug("Processing vehicle: {}", vehicleDto);
        kafkaProducerService.send(null, vehicleDto);
    }
}
