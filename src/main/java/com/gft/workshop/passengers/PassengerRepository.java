package com.gft.workshop.passengers;

import com.gft.workshop.passengers.model.Passenger;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


public interface PassengerRepository extends ReactiveCrudRepository<Passenger, Integer> {

    Mono<Passenger> findByPassengersId(Integer id);

}
