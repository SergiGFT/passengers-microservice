package com.gft.workshop.passengers.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "trips")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    private String tripId;
    private String routeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startStop;
    private String endStop;
    private double fare;
    private String passengerId;
    private TripStatus status = TripStatus.IN_PROGRESS;

    public enum TripStatus {
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

}
