package com.gft.workshop.passengers.infraestructure.config;

import com.gft.workshop.passengers.domain.model.Passenger;
import com.gft.workshop.passengers.domain.model.Trip;
import com.gft.workshop.passengers.domain.model.TripStatus;
import com.gft.workshop.passengers.domain.repository.PassengerRepository;
import com.gft.workshop.passengers.domain.repository.TripRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
@AllArgsConstructor
public class DataInitializer {

  private final PassengerRepository passengerRepository;
  private final TripRepository tripRepository;

  @Bean
  CommandLineRunner initPassengers() {
    return args -> {
      Flux<Passenger> passengers =
          Flux.just(
              Passenger.builder()
                  .passengerId(1L)
                  .name("John Doe")
                  .email("john.doe@example.com")
                  .build(),
              Passenger.builder()
                  .passengerId(2L)
                  .name("Jane Smith")
                  .email("jane.smith@example.com")
                  .build(),
              Passenger.builder()
                  .passengerId(3L)
                  .name("Michael Johnson")
                  .email("michael.johnson@example.com")
                  .build());

      Flux<Trip> trips =
          Flux.just(
              Trip.builder()
                  .tripId(1L)
                  .passengerId(2L)
                  .startTime(LocalDateTime.parse("2024-11-02T00:00:00"))
                  .endTime(LocalDateTime.parse("2024-11-02T00:00:00"))
                  .status(TripStatus.IN_PROGRESS)
                  .build(),
              Trip.builder()
                  .tripId(2L)
                  .passengerId(3L)
                  .startTime(LocalDateTime.parse("2024-12-02T00:00:00"))
                  .endTime(LocalDateTime.parse("2024-12-02T00:00:00"))
                  .status(TripStatus.IN_PROGRESS)
                  .build());

      passengerRepository.deleteAll().thenMany(passengerRepository.saveAll(passengers)).subscribe();

      tripRepository.deleteAll().thenMany(tripRepository.saveAll(trips)).subscribe();
    };
  }
}
