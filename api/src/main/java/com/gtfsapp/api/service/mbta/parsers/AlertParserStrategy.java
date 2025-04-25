package com.gtfsapp.api.service.mbta.parsers;

import com.google.transit.realtime.GtfsRealtime;
import com.gtfsapp.api.model.dto.AlertDto;
import com.gtfsapp.api.service.interfaces.GtfsFeedParser;
import com.gtfsapp.api.service.mbta.mappers.AlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("alertParserStrategy")
@RequiredArgsConstructor
public class AlertParserStrategy implements GtfsFeedParser<AlertDto> {

    private final AlertMapper alertMapper;

    @Override
    public List<AlertDto> parse(GtfsRealtime.FeedMessage feed) {
        return feed.getEntityList().stream()
                .filter(GtfsRealtime.FeedEntity::hasAlert)
                .map(entity -> alertMapper.mapToDto(entity.getAlert()))
                .toList();
    }
}
