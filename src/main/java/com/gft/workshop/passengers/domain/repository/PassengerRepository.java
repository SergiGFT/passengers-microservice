package com.gft.workshop.passengers.domain.repository;

import com.gft.workshop.passengers.domain.model.Passenger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends ReactiveMongoRepository<Passenger, String> {}
