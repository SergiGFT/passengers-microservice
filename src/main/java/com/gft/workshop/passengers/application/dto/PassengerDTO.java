package com.gft.workshop.passengers.application.dto;

import com.gft.workshop.passengers.domain.model.Address;
import com.gft.workshop.passengers.domain.model.Trip;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PassengerDTO {
  private String name;
  private String email;
  private String phone;
  private Address address;
  private String preferredPaymentMethod;

  private List<Trip> tripsHistorical;
}
