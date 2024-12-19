package com.gft.workshop.passengers.application.service;

import com.gft.workshop.passengers.application.dto.PassengerDTO;
import com.gft.workshop.passengers.domain.exceptions.UserNotFoundException;
import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.model.Trip;
import com.gft.workshop.passengers.domain.repository.PassengerRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PassengerService {

  private final PassengerRepository passengerRepository;

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public Mono<Passenger> createPassenger(PassengerDTO passengerDTO) {
    Passenger passenger =
        Passenger.builder()
            .phone(passengerDTO.getPhone())
            .address(passengerDTO.getAddress())
            .email(passengerDTO.getEmail())
            .name(passengerDTO.getName())
            .preferredPaymentMethod(passengerDTO.getPreferredPaymentMethod())
            .build();
    passenger.setRegisteredAt(Date.from(Instant.now()));
    return this.passengerRepository.save(passenger);
  }

  public Flux<Passenger> findAllPassengers() {
    return passengerRepository.findAll();
  }

  public Mono<Passenger> findPassenger(String passengerId) {
    return passengerRepository
        .findById(passengerId)
        .switchIfEmpty(Mono.error(new UserNotFoundException(passengerId)));
  }

  public Flux<Passenger> findPassengersWithTripsInDateRange(
      LocalDate startDate, LocalDate endDate) {

    Query query = new Query();

    query.addCriteria(Criteria.where("startTime").gte(startDate).and("endTime").lte(endDate));

    return reactiveMongoTemplate
        .find(query, Trip.class)
        .map(Trip::getPassengerId)
        .distinct()
        .flatMap(this::findPassenger);
  }

  public Mono<Void> deletePassenger(String id) {
    return passengerRepository.deleteById(id);
  }
}
