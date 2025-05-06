package com.gtfsapp.api.service.kafka;

import com.gtfsapp.api.service.mbta.AlertService;
import com.gtfsapp.api.service.mbta.FeedDownloader;
import com.gtfsapp.api.service.mbta.VehicleService;
import com.gtfsapp.api.service.mbta.parsers.AlertParserStrategy;
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
    private AlertParserStrategy alertParser;
    private VehicleService vehicleService;
    private AlertService alertService;

    @Autowired
    public void setFeedDownloader(FeedDownloader feedDownloader) {
        this.feedDownloader = feedDownloader;
    }

    @Autowired
    public void setVehicleParser(VehicleParserStrategy vehicleParser) {
        this.vehicleParser = vehicleParser;
    }

    @Autowired
    public void setAlertParser(AlertParserStrategy alertParser) {
        this.alertParser = alertParser;
    }

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Autowired
    public void setAlertService(AlertService alertService) {
        this.alertService = alertService;
    }

    @Scheduled(fixedDelay = 5000)
    public void pollVehicleFeed() {
        feedDownloader.download("https://cdn.mbta.com/realtime/VehiclePositions.pb")
                .ifPresent(feed -> vehicleParser.parse(feed).forEach(vehicleService::process));
    }

    @Scheduled(fixedDelay = 5000)
    public void pollAlertsFeed() {
        feedDownloader.download("https://cdn.mbta.com/realtime/Alerts.pb")
                .ifPresent(feed -> alertParser.parse(feed).forEach(alertService::process));
    }
}
