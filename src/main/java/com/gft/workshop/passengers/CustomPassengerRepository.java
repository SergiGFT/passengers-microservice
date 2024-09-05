package com.gft.workshop.passengers;

import com.gft.workshop.passengers.model.Passenger;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomPassengerRepository {

    final R2dbcEntityTemplate  template;

    public CustomPassengerRepository(R2dbcEntityTemplate template) {
        this.template = template;
    }

    public Mono<Passenger> insertPassenger(Passenger passenger) {
        return template.insert(Passenger.class)
                .using(passenger);

    }
}
