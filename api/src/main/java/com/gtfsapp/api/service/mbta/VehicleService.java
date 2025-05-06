package com.gtfsapp.api.service.mbta;

import com.gtfsapp.api.model.dto.VehicleDto;
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
public class VehicleService implements DataProcessing<VehicleDto> {

    private KafkaProducerService kafkaProducerService;
    private String vehicleTopic;

    @Autowired
    public void setKafkaProducerService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Value("${kafka.topic-vehicles}")
    public void setVehicleTopic(String vehicleTopic) {
        this.vehicleTopic = vehicleTopic;
    }

    @Override
    public void process(VehicleDto dto) {
        log.info("Processing vehicle: {}", dto);
        kafkaProducerService.send(vehicleTopic, dto);
    }
}
