package com.gft.workshop.passengers.application.service;

import com.gft.workshop.passengers.application.dto.TripDTO;
import com.gft.workshop.passengers.domain.model.Trip;
import com.gft.workshop.passengers.domain.repository.PassengerRepository;
import com.gft.workshop.passengers.domain.repository.TripRepository;
import io.hypersistence.tsid.TSID;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TripService {
  private final TripRepository tripRepository;
  private final PassengerRepository passengerRepository;

  public Mono<Trip> addTripToPassenger(long passengerId, TripDTO tripDTO) {

    return passengerRepository
        .findById(passengerId)
        .flatMap(
            passenger -> {
              Trip trip =
                  Trip.builder()
                      .tripId(TSID.Factory.getTsid().toLong())
                      .routeId(tripDTO.getRouteId())
                      .startStop(tripDTO.getStartStop())
                      .fare(tripDTO.getFare())
                      .status(tripDTO.getStatus())
                      .passengerId(tripDTO.getPassengerId())
                      .endTime(tripDTO.getEndTime())
                      .build();
              trip.setPassengerId(passengerId);
              return tripRepository.save(trip);
            });
  }

  public Flux<Trip> getTripsByPassengerId(long passengerId) {
    return tripRepository.findAllByPassengerId(passengerId);
  }

  public Mono<Long> markTripAsCompleted(long tripId) {
    return tripRepository.markTripAsCompleted(tripId, LocalDateTime.now());
  }
}
