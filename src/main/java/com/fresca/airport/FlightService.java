package com.fresca.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;


    // Create flight
    public Flight createFlight(Flight flight, Long fromAirportId) {
        Optional<Airport> airportOpt = airportRepository.findById(fromAirportId);
        if (airportOpt.isEmpty()) {
            throw new IllegalArgumentException("Airport not found with id: " + fromAirportId);
        }

        flight.setFromAirport(airportOpt.get());
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public Optional<Flight> getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    public List<Flight> getFlightsByAirportCode(String airportCode) {
        return flightRepository.findByFromAirportCode(airportCode);
    }

    public Flight updateFlight(Long id, Flight flightDetails) {
        Optional<Flight> flightOpt = flightRepository.findById(id);
        if (flightOpt.isEmpty()) {
            throw new IllegalArgumentException("Flight not found with id: " + id);
        }

        Flight flight = flightOpt.get();
        flight.setFlightNumber(flightDetails.getFlightNumber());
        flight.setScheduledTime(flightDetails.getScheduledTime());
        flight.setStatus(flightDetails.getStatus());

        return flightRepository.save(flight);
    }

    public Flight updateFlightStatus(Long id, String status) {
        Optional<Flight> flightOpt = flightRepository.findById(id);
        if (flightOpt.isEmpty()) {
            throw new IllegalArgumentException("Flight not found with id: " + id);
        }

        Flight flight = flightOpt.get();
        flight.setStatus(status);

        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        Optional<Flight> flightOpt = flightRepository.findById(id);
        if (flightOpt.isEmpty()) {
            throw new IllegalArgumentException("Flight not found with id: " + id);
        }

        flightRepository.delete(flightOpt.get());
    }
}
