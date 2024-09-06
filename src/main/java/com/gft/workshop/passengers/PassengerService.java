package com.gft.workshop.passengers;

import com.gft.workshop.passengers.model.Passenger;
import com.gft.workshop.passengers.model.Trip;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;

    private final CustomPassengerRepository customPassengerRepository;

    private final TripRepository tripRepository;

    public Mono<Passenger> createPassenger(Passenger passenger) {
        passenger.setRegisteredAt(ZonedDateTime.now());
        return this.customPassengerRepository.insertPassenger(passenger);
    }

    public Mono<Trip> createTrip(String passengerId, Trip trip) {

        return passengerRepository.findById(passengerId)
                        .flatMap(passenger -> {
                            trip.setPassenger(passenger);
                            return tripRepository.insertTripToPassenger(trip);
                        });

    }

    public Flux<Passenger> findAllPassengers() {
        return passengerRepository.findAll();
    }

    public Mono<Passenger> findPassenger(String passengerId) {
        return passengerRepository.findById(passengerId);
    }


}
