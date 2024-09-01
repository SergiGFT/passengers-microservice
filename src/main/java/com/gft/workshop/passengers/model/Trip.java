package com.gft.workshop.passengers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripId;
    private String routeId;
    private String vehicleId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String startStop;
    private String endStop;
    private double fare;

    @ManyToOne
    @JoinColumn(name = "passengerId")
    private Passenger passenger;

}
