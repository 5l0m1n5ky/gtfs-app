package com.gtfsapp.api.service.kafka;

import com.gtfsapp.api.service.mbta.AlertService;
import com.gtfsapp.api.service.mbta.FeedDownloader;
import com.gtfsapp.api.service.mbta.VehicleService;
import com.gtfsapp.api.service.mbta.parsers.AlertParserStrategy;
import com.gtfsapp.api.service.mbta.parsers.VehicleParserStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final FeedDownloader feedDownloader;
    private final VehicleParserStrategy vehicleParser;
    private final AlertParserStrategy alertParser;
    private final VehicleService vehicleService;
    private final AlertService alertService;

    @Scheduled(fixedDelay = 1000)
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
