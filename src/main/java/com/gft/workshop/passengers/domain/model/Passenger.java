package com.gft.workshop.passengers.domain.model;

import lombok.*;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "passengers")
@AggregateRoot
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Date registeredAt;

    @Transient
    private List<Trip> tripsHistorical;

}
