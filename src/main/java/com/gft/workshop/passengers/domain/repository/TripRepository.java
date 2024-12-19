package com.gft.workshop.passengers.domain.repository;

import com.gft.workshop.passengers.domain.model.Trip;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TripRepository extends ReactiveMongoRepository<Trip, String> {

  @Query("{'passengerId': ?0 }")
  Flux<Trip> findAllByPassengerId(String passengerId);

  @Query("{ 'tripId': ?0, 'status': 'IN_PROGRESS' }")
  @Update("{ '$set': { 'status': 'COMPLETED', 'endTime': ?1 } }")
  Mono<Long> markTripAsCompleted(String tripId, LocalDateTime endTime);
}
