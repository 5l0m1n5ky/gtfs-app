package com.gtfsapp.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "directions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direction {

    @Id
    @Column(name = "direction_id")
    private String directionId;

    @Column(name = "route_id")
    private String routeId;

    @Column(name = "direction_name")
    private String directionName;

    @Column(name = "direction_destination")
    private String directionDestination;
}
