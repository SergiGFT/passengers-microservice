package com.gft.workshop.passengers.application.controller;

import static org.springframework.http.HttpMethod.*;

import com.gft.workshop.passengers.application.dto.PassengerDTO;
import com.gft.workshop.passengers.application.service.PassengerService;
import com.gft.workshop.passengers.domain.model.Passenger;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/passenger")
@RequiredArgsConstructor
@Slf4j
public class PassengerController {

  private static final String PATH = "/passenger";

  private final PassengerService passengerService;

  @PostMapping
  public Mono<Passenger> createPassenger(@RequestBody PassengerDTO passenger) {

    return passengerService
        .createPassenger(passenger)
        .doOnSuccess(e -> ControllerLogs.logOnSuccess(log, POST, PATH))
        .doOnCancel(() -> ControllerLogs.logOnCancel(log, POST, PATH))
        .doOnError(errorEx -> ControllerLogs.logOnError(log, POST, PATH, errorEx));
  }

  @GetMapping("/{id}")
  public Mono<Passenger> findPassengerById(@PathVariable String id) {
    var path = PATH + id;
    return passengerService
        .findPassenger(id)
        .doOnSuccess(e -> ControllerLogs.logOnSuccess(log, GET, path))
        .doOnCancel(() -> ControllerLogs.logOnCancel(log, GET, path))
        .doOnError(errorEx -> ControllerLogs.logOnError(log, GET, path, errorEx));
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deletePassengerById(@PathVariable String id) {
    var path = PATH + id;
    return passengerService
        .deletePassenger(id)
        .doOnSuccess(e -> ControllerLogs.logOnSuccess(log, DELETE, path))
        .doOnCancel(() -> ControllerLogs.logOnCancel(log, DELETE, path))
        .doOnError(errorEx -> ControllerLogs.logOnError(log, DELETE, path, errorEx));
  }

  @GetMapping
  public Flux<Passenger> findAllPassengers() {
    return passengerService
        .findAllPassengers()
        .doOnComplete(() -> ControllerLogs.logOnSuccess(log, GET, PATH))
        .doOnCancel(() -> ControllerLogs.logOnCancel(log, GET, PATH))
        .doOnError(errorEx -> ControllerLogs.logOnError(log, GET, PATH, errorEx));
  }

  @GetMapping("passenger/{startDate}/{endDate}")
  public Flux<Passenger> getPassengersWithTripsInDateRange(
      @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
    return passengerService
        .findPassengersWithTripsInDateRange(startDate, endDate)
        .doOnComplete(() -> ControllerLogs.logOnSuccess(log, GET, PATH))
        .doOnCancel(() -> ControllerLogs.logOnCancel(log, GET, PATH))
        .doOnError(errorEx -> ControllerLogs.logOnError(log, GET, PATH, errorEx));
  }
}
