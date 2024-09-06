package com.gft.workshop.passengers;

import com.gft.workshop.passengers.model.Passenger;
import com.gft.workshop.passengers.model.Trip;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TripRepository {

    final R2dbcEntityTemplate template;

    public TripRepository(R2dbcEntityTemplate template) {
        this.template = template;
    }

    public Mono<Trip> insertTripToPassenger(Trip trip) {
        return template.insert(Trip.class)
                .using(trip);

    }
}