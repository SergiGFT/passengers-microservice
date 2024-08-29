package com.gft.workshop.passengers.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    private String passengerId;
    private String name;
    private String email;
    private String phone;
    private String preferredPaymentMethod;
    private ZonedDateTime registeredAt;
    private List<Trip> trips;
}
