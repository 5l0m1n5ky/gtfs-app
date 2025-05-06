package com.gtfsapp.api.service.kafka;

import com.gtfsapp.api.service.mbta.FeedDownloader;
import com.gtfsapp.api.service.mbta.VehicleService;
import com.gtfsapp.api.service.mbta.parsers.VehicleParserStrategy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private FeedDownloader feedDownloader;
    private VehicleParserStrategy vehicleParser;
    private VehicleService vehicleService;

    @Autowired
    public void setFeedDownloader(FeedDownloader feedDownloader) {
        this.feedDownloader = feedDownloader;
    }

    @Autowired
    public void setVehicleParser(VehicleParserStrategy vehicleParser) {
        this.vehicleParser = vehicleParser;
    }

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Scheduled(fixedDelay = 5000)
    public void pollVehicleFeed() {
        feedDownloader.download("https://cdn.mbta.com/realtime/VehiclePositions.pb")
                .ifPresent(feed -> vehicleParser.parse(feed).forEach(vehicleService::process));
    }
}
