package com.gtfsapp.api.service.gtfs;

import com.gtfsapp.api.model.dto.VehicleDto;
import com.gtfsapp.api.service.interfaces.DataProcessing;
import com.gtfsapp.api.service.kafka.KafkaProducerService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class VehicleService implements DataProcessing<VehicleDto> {

    private KafkaProducerService kafkaProducerService;
    private String vehicleTopic;
    private SimpMessagingTemplate wsTemplate;

    @Autowired
    public void setKafkaProducerService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Autowired
    public void setWsTemplate(SimpMessagingTemplate wsTemplate) {
        this.wsTemplate = wsTemplate;
    }

    @Value("${kafka.topic.vehicles}")
    public void setVehicleTopic(String vehicleTopic) {
        this.vehicleTopic = vehicleTopic;
    }

    @Override
    public void process(VehicleDto dto) {
        kafkaProducerService.send(vehicleTopic, dto);
    }
}
