package com.gft.workshop.passengers.application.dto;

import com.gft.workshop.passengers.domain.model.TripStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripDTO {

  private long tripId;
  private long routeId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private long startStop;
  private long endStop;
  private double fare;
  private long passengerId;
  private TripStatus status;
}
