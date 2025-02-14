package com.traflux.TrafluxTrafficLightService.entities;

import com.traflux.TrafluxTrafficLightService.enums.LightStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="lights")
@Data
public class LightModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private LightStatus lightStatus;

    @Column(nullable = false)
    private Long duration;
}
