package com.gtfsapp.api.service.mbta.mappers;

import com.gtfsapp.api.model.dto.AlertDto;
import com.google.transit.realtime.GtfsRealtime;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@NoArgsConstructor
public class AlertMapper {

    public AlertDto mapToDto(GtfsRealtime.Alert alert) {
        return AlertDto.builder()
                .id(generateId(alert))
                .startTime(alert.getActivePeriodCount() > 0 ? Instant.ofEpochSecond(alert.getActivePeriod(0).getStart()) : null)
                .endTime(alert.getActivePeriodCount() > 0 ? Instant.ofEpochSecond(alert.getActivePeriod(0).getEnd()) : null)
                .headerText(getText(alert.getHeaderText()))
                .descriptionText(getText(alert.getDescriptionText()))
                .cause(alert.hasCause() ? alert.getCause().name() : null)
                .effect(alert.hasEffect() ? alert.getEffect().name() : null)
                .informedRouteIds(
                        alert.getInformedEntityList().stream()
                                .map(GtfsRealtime.EntitySelector::getRouteId)
                                .filter(Objects::nonNull)
                                .toList()
                )
                .informedStopIds(
                        alert.getInformedEntityList().stream()
                                .map(GtfsRealtime.EntitySelector::getStopId)
                                .filter(Objects::nonNull)
                                .toList()
                )
                .build();
    }

    private String getText(GtfsRealtime.TranslatedString string) {
        return string.getTranslationList().isEmpty() ? "" : string.getTranslation(0).getText();
    }

    private String entityToString(GtfsRealtime.EntitySelector entity) {
        List<String> parts = new ArrayList<>();

        if (entity.hasAgencyId()) {
            parts.add("agency:" + entity.getAgencyId());
        }
        if (entity.hasRouteId()) {
            parts.add("route:" + entity.getRouteId());
        }
        if (entity.hasStopId()) {
            parts.add("stop:" + entity.getStopId());
        }

        return String.join(" ", parts);
    }

    private String generateId(GtfsRealtime.Alert alert) {
        return String.valueOf(alert.hashCode());
    }
}
