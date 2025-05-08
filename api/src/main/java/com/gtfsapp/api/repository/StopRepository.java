package com.gtfsapp.api.repository;

import com.gtfsapp.api.model.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, String> {
}
