package com.gtfsapp.api.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDto {

    private String tripId;
    private String routeId;
    private String serviceId;
    private String tripHeadsign;
    private String tripShortName;
    private Integer directionId;
    private String blockId;
    private String shapeId;
    private Integer wheelchairAccessible;
    private Integer tripRouteType;
    private String routePatternId;
    private Integer bikesAllowed;
}
