package com.gtfsapp.api.service.mbta.mappers;

import com.gtfsapp.api.model.dto.VehicleDto;
import com.gtfsapp.api.model.dto.VehicleDto.OccupancyStatus;
import com.gtfsapp.api.model.dto.VehicleDto.VehicleStatus;
import com.google.transit.realtime.GtfsRealtime;

import java.time.Instant;
import java.util.UUID;

public class VehicleMapper {

    public VehicleDto mapToDto(GtfsRealtime.VehiclePosition position) {
        return VehicleDto.builder()
                .vehicleId(position.hasVehicle() ? position.getVehicle().getId() : null)
                .routeId(position.hasTrip() ? position.getTrip().getRouteId() : null)

                .latitude(position.getPosition().getLatitude())
                .longitude(position.getPosition().getLongitude())
                .bearing(position.getPosition().hasBearing() ? position.getPosition().getBearing() : null)
                .speed(position.getPosition().hasSpeed() ? position.getPosition().getSpeed() : null)

                .timestamp(position.hasTimestamp() ? Instant.ofEpochSecond(position.getTimestamp()) : null)

                .stopId(position.hasStopId() ? position.getStopId() : null)
                .currentStatus(position.hasCurrentStatus() ? mapStatus(position.getCurrentStatus()) : null)
                .occupancyStatus(position.hasOccupancyStatus() ? mapOccupancy(position.getOccupancyStatus()) : null)

                .build();
    }

    private VehicleStatus mapStatus(GtfsRealtime.VehiclePosition.VehicleStopStatus status) {
        return switch (status) {
            case IN_TRANSIT_TO -> VehicleStatus.IN_TRANSIT_TO;
            case STOPPED_AT -> VehicleStatus.STOPPED_AT;
            case INCOMING_AT -> VehicleStatus.INCOMING_AT;
        };
    }

    private OccupancyStatus mapOccupancy(GtfsRealtime.VehiclePosition.OccupancyStatus status) {
        return switch (status) {
            case EMPTY -> OccupancyStatus.EMPTY;
            case MANY_SEATS_AVAILABLE -> OccupancyStatus.MANY_SEATS_AVAILABLE;
            case FEW_SEATS_AVAILABLE -> OccupancyStatus.FEW_SEATS_AVAILABLE;
            case STANDING_ROOM_ONLY -> OccupancyStatus.STANDING_ROOM_ONLY;
            case CRUSHED_STANDING_ROOM_ONLY -> OccupancyStatus.CRUSHED_STANDING_ROOM_ONLY;
            case FULL -> OccupancyStatus.FULL;
            case NOT_ACCEPTING_PASSENGERS -> OccupancyStatus.NOT_ACCEPTING_PASSENGERS;
            default -> null;
        };
    }

    public String generateId(GtfsRealtime.VehiclePosition position) {
        return position.hasVehicle() && position.getVehicle().hasId()
                ? "vehicle-" + position.getVehicle().getId()
                : UUID.randomUUID().toString();
    }

    public String getText(GtfsRealtime.TranslatedString translatedString) {
        if (translatedString.getTranslationCount() > 0) {
            return translatedString.getTranslation(0).getText();
        }
        return null;
    }

    public String entityToString(GtfsRealtime.EntitySelector entity) {
        String result;

        if (entity.hasRouteId()) {
            result = "route:" + entity.getRouteId();
        } else if (entity.hasStopId()) {
            result = "stop:" + entity.getStopId();
        } else if (entity.hasTrip() && entity.getTrip().hasTripId()) {
            result = "trip:" + entity.getTrip().getTripId();
        } else {
            result = "unknown";
        }

        return result;
    }
}

