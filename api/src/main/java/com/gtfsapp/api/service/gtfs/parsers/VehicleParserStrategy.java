package com.gtfsapp.api.service.gtfs.parsers;

import com.google.transit.realtime.GtfsRealtime;
import com.gtfsapp.api.model.dto.VehicleDto;
import com.gtfsapp.api.service.interfaces.GtfsFeedParser;
import com.gtfsapp.api.service.gtfs.mappers.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("vehicleParserStrategy")
@RequiredArgsConstructor
public class VehicleParserStrategy implements GtfsFeedParser<VehicleDto> {

    private final VehicleMapper vehicleMapper;

    public List<VehicleDto> parse(GtfsRealtime.FeedMessage feed) {
        return feed.getEntityList().stream()
                .filter(GtfsRealtime.FeedEntity::hasVehicle)
                .map(entity -> vehicleMapper.mapToDto(entity.getVehicle()))
                .toList();
    }
}

