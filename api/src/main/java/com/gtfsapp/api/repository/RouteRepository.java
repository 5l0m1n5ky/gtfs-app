package com.gtfsapp.api.repository;

import com.gtfsapp.api.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, String> {
}
