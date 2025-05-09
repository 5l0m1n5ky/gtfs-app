package com.gtfsapp.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "stops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stop {

    @Id
    @Column(name = "stop_id")
    private String stopId;

    @Column(name = "stop_code")
    private String stopCode;

    @Column(name = "stop_name")
    private String stopName;

    @Column(name = "stop_desc")
    private String stopDesc;

    @Column(name = "platform_code")
    private String platformCode;

    @Column(name = "platform_name")
    private String platformName;

    @Column(name = "stop_lat")
    private Double stopLat;

    @Column(name = "stop_lon")
    private Double stopLon;

    @Column(name = "zone_id")
    private String zoneId;

    @Column(name = "stop_address")
    private String stopAddress;

    @Column(name = "stop_url")
    private String stopUrl;

    @Column(name = "level_id")
    private String levelId;

    @Column(name = "location_type")
    private Integer locationType;

    @Column(name = "parent_station")
    private String parentStation;

    @Column(name = "wheelchair_boarding")
    private Integer wheelchairBoarding;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "on_street")
    private String onStreet;

    @Column(name = "at_street")
    private String atStreet;

    @Column(name = "vehicle_type")
    private Integer vehicleType;
}
