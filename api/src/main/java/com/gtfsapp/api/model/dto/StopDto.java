package com.gtfsapp.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StopDto {

    private String stopId;
    private String stopName;
    private Double stopLat;
    private Double stopLon;
}
