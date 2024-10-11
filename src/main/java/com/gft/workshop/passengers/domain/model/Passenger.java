package com.gft.workshop.passengers.domain.model;

import lombok.*;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.List;

@Table("passenger")
@AggregateRoot
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    private String passengerId;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private String phone;
    private Address address;
    private String preferredPaymentMethod;
    private ZonedDateTime registeredAt;
    private String passengerStatus;

    private List<Trip> tripsHistorical;

}
