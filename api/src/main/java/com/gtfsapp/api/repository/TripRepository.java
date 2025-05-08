package com.gtfsapp.api.repository;

import com.gtfsapp.api.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, String> {
}
