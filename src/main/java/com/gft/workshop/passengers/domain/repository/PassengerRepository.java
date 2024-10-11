package com.gft.workshop.passengers.domain.repository;

import com.gft.workshop.passengers.domain.model.Passenger;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends ReactiveCrudRepository<Passenger, String> {



}
