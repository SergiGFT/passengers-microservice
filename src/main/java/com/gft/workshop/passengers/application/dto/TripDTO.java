package com.gft.workshop.passengers.application.dto;

import com.gft.workshop.passengers.domain.model.TripStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripDTO {

  private String tripId;
  private String routeId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String startStop;
  private String endStop;
  private double fare;
  private String passengerId;
  private TripStatus status;
}
