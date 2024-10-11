package com.gft.workshop.passengers.infraestructure.repository;

import com.gft.workshop.passengers.domain.model.Trip;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomTripRepository {

    final R2dbcEntityTemplate template;

    public CustomTripRepository(R2dbcEntityTemplate template) {
        this.template = template;
    }

    public Mono<Trip> insertTripToPassenger(Trip trip) {
        return template.insert(Trip.class)
                .using(trip);

    }
}