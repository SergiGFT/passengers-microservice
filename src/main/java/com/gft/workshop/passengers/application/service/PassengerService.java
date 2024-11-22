package com.gft.workshop.passengers.application.service;

import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.model.Trip;
import com.gft.workshop.passengers.domain.repository.PassengerRepository;
import com.gft.workshop.passengers.domain.repository.TripRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Service
@AllArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;

    private final TripRepository tripRepository;

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Passenger> createPassenger(Passenger passenger) {
        passenger.setRegisteredAt(Date.from(Instant.now()));
        return this.passengerRepository.save(passenger);
    }

    public Mono<Trip> addTripToPassenger(String passengerId, Trip trip) {

        return passengerRepository.findById(passengerId)
                        .flatMap(passenger -> {
                            trip.setPassengerId(passengerId);
                            return tripRepository.save(trip);
                        });
    }

    public Flux<Passenger> findAllPassengers() {
        return passengerRepository.findAll();
    }

    public Mono<Passenger> findPassenger(String passengerId) {
        return passengerRepository.findById(passengerId);
    }

    public Flux<Trip> getTripsByPassengerId(String passengerId) {
        return tripRepository.findAllByPassengerId(passengerId);
    }

    public Mono<Long> markTripAsCompleted(String tripId) {
        return tripRepository.markTripAsCompleted(tripId, LocalDateTime.now());
    }

    public Flux<Passenger> findPassengersWithTripsInDateRange(LocalDate startDate, LocalDate endDate) {

        Query query = new Query();

        query.addCriteria(Criteria.where("startTime").gte(startDate).and("endTime").lte(endDate));

        return reactiveMongoTemplate.find(query, Trip.class)
                .map(Trip::getPassengerId).distinct().flatMap(this::findPassenger);
    }

}
