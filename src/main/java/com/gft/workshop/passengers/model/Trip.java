package com.gft.workshop.passengers.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table("trip")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    private Integer tripId;
    private String routeId;
    private String vehicleId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String startStop;
    private String endStop;
    private double fare;
    private Passenger passenger;

}
