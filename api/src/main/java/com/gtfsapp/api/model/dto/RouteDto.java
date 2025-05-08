package com.gtfsapp.api.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {

    private String routeId;
    private String routeShortName;
    private String routeLongName;
    private String routeType;
}
