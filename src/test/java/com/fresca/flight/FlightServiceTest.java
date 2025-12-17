package com.fresca.flight;

import com.fresca.airport.Airport;
import com.fresca.airport.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    FlightRepository flightRepository;

    @Mock
    AirportRepository airportRepository;

    @InjectMocks
    FlightService flightService;


    @Test
    void shouldCreateFlightAndReturnSaved() {
        Flight input = new Flight();
        input.setFlightNumber("AC101");
        input.setScheduledTime(LocalDateTime.of(2024, 12, 15, 8, 30));
        input.setStatus("On Time");

        Airport airport = new Airport("St. John's Airport", "YYT");
        airport.setId(1L);
        Airport airport2 = new Airport("Some Other Airport", "SOA");
        airport2.setId(2L);

        Flight saved = new Flight();
        saved.setId(1L);
        saved.setFlightNumber("AC101");
        saved.setFromAirport(airport);
        saved.setToAirport(airport2);
        saved.setScheduledTime(LocalDateTime.of(2024, 12, 15, 8, 30));
        saved.setStatus("On Time");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(airport2));
        when(flightRepository.save(any(Flight.class))).thenReturn(saved);

        Flight result = flightService.createFlight(input, 1L, 2L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("AC101", result.getFlightNumber());
        assertEquals("On Time", result.getStatus());
        verify(flightRepository).save(any(Flight.class));
    }


    @Test
    void shouldFindFlightById() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AC101");
        flight.setStatus("On Time");

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Optional<Flight> result = flightService.getFlightById(1L);

        assertTrue(result.isPresent());
        assertEquals("AC101", result.get().getFlightNumber());
        verify(flightRepository).findById(1L);
    }


    @Test
    void shouldUpdateFlightStatus() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AC101");
        flight.setStatus("On Time");

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        Flight result = flightService.updateFlightStatus(1L, "Delayed");

        assertNotNull(result);
        assertEquals("Delayed", flight.getStatus());
        verify(flightRepository).findById(1L);
        verify(flightRepository).save(flight);
    }


    @Test
    void shouldDeleteFlight() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AC101");

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        flightService.deleteFlight(1L);

        verify(flightRepository).findById(1L);
        verify(flightRepository).delete(flight);
    }
}






















