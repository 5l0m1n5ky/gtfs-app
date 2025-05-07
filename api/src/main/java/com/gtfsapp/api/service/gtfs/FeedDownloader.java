package com.gtfsapp.api.service.gtfs;

import com.google.transit.realtime.GtfsRealtime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@Slf4j
public class FeedDownloader {

    private final WebClient webClient = WebClient.create();

    public Optional<GtfsRealtime.FeedMessage> download(String url) {
        try {
            byte[] data = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();

            return Optional.of(GtfsRealtime.FeedMessage.parseFrom(data));
        } catch (Exception exception) {
            log.error("Failed to download or parse feed from: " + url, exception);
            return Optional.empty();
        }
    }
}
