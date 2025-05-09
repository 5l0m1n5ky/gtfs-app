package com.gtfsapp.api.model.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDto {

    private String routeId;
    private String agencyId;
    private String routeShortName;
    private String routeLongName;
    private String routeDesc;
    private Integer routeType;
    private String routeUrl;
    private String routeColor;
    private String routeTextColor;
    private Integer routeSortOrder;
    private String routeFareClass;
    private String lineId;
    private String listedRoute;
    private String networkId;
}
