package com.gft.workshop.passengers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    private String tripId;
    private String routeId;
    private String vehicleId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String startStop;
    private String endStop;
    private double fare;
}
