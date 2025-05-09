package com.gtfsapp.api.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopDto {

    private String stopId;
    private String stopCode;
    private String stopName;
    private String stopDesc;
    private String platformCode;
    private String platformName;
    private Double stopLat;
    private Double stopLon;
    private String zoneId;
    private String stopAddress;
    private String stopUrl;
    private String levelId;
    private Integer locationType;
    private String parentStation;
    private Integer wheelchairBoarding;
    private String municipality;
    private String onStreet;
    private String atStreet;
    private Integer vehicleType;
}
