package com.traflux.TrafluxTrafficLightService.repositories;

import com.traflux.TrafluxTrafficLightService.entities.LightModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightRepository extends JpaRepository<LightModel, Long> {
}
