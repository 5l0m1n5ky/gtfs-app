package com.gtfsapp.api.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
@Builder
public class AlertDto {

    String id;
    Instant startTime;
    Instant endTime;

    String headerText;
    String descriptionText;
    String cause;
    String effect;

    List<String> informedRouteIds;
    List<String> informedStopIds;
}
