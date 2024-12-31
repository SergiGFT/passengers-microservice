package com.gft.workshop.passengers.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.jmolecules.ddd.annotation.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trips")
@Entity
@Data
@Builder
public class Trip {

  @Id private long tripId;
  private long routeId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private long startStop;
  private long endStop;
  private double fare;
  private long passengerId;
  private TripStatus status;
}
