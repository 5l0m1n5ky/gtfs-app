package com.gtfsapp.api.service.gtfs;

import com.gtfsapp.api.model.dto.VehicleDto;
import com.gtfsapp.api.service.kafka.KafkaProducerService;
import com.gtfsapp.api.service.gtfs.parsers.VehicleParserStrategy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class VehicleFeedProducer {

    private FeedDownloader feedDownloader;
    private VehicleParserStrategy vehicleParser;
    private KafkaProducerService producerService;
    private String vehicleTopic;

    @Autowired
    public void setFeedDownloader(FeedDownloader feedDownloader) {
        this.feedDownloader = feedDownloader;
    }

    @Autowired
    public void setVehicleParser(VehicleParserStrategy vehicleParser) {
        this.vehicleParser = vehicleParser;
    }

    @Autowired
    public void setProducerService(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @Value("${kafka.topic.vehicles}")
    public void setVehicleTopic(String vehicleTopic) {
        this.vehicleTopic = vehicleTopic;
    }

    @Scheduled(fixedDelayString = "${data.fetch.poll.duration}")
    public void pollAndProduce() {
        log.debug("Polling vehicle feed");
        feedDownloader
                .download("https://cdn.mbta.com/realtime/VehiclePositions.pb")
                .ifPresent(feed -> {
                    List<VehicleDto> vehicles = vehicleParser.parse(feed);
                    log.debug("Sending {} vehicles to Kafka", vehicles.size());
                    producerService.send(vehicleTopic, vehicles);
                });
    }
}

