package com.gtfsapp.api.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDto {

    private String vehicleId;
    private String routeId;

    private double latitude;
    private double longitude;

    private Float bearing;
    private Float speed;

    private Instant timestamp;

    private String stopId;
    private VehicleStatus currentStatus;
    private OccupancyStatus occupancyStatus;

    public enum VehicleStatus {
        IN_TRANSIT_TO,
        STOPPED_AT,
        INCOMING_AT
    }

    public enum OccupancyStatus {
        EMPTY,
        MANY_SEATS_AVAILABLE,
        FEW_SEATS_AVAILABLE,
        STANDING_ROOM_ONLY,
        CRUSHED_STANDING_ROOM_ONLY,
        FULL,
        NOT_ACCEPTING_PASSENGERS
    }
}
