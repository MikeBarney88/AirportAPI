package com.fresca.flight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
@CrossOrigin(origins = "http://localhost:5173")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight,
                                               @RequestParam Long fromAirportId) {
        try {
            Flight createdFlight = flightService.createFlight(flight, fromAirportId);
            return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flightOpt = flightService.getFlightById(id);
        if (flightOpt.isPresent()) {
            return new ResponseEntity<>(flightOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/number/{flightNumber}")
    public ResponseEntity<Flight> getFlightByNumber(@PathVariable String flightNumber) {
        Optional<Flight> flightOpt = flightService.getFlightByFlightNumber(flightNumber);
        if (flightOpt.isPresent()) {
            return new ResponseEntity<>(flightOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/airport/{code}")
    public ResponseEntity<List<Flight>> getFlightsByAirportCode(@PathVariable String code) {
        List<Flight> flights = flightService.getFlightsByAirportCode(code);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id,
                                               @RequestBody Flight flight) {
        try {
            Flight updatedFlight = flightService.updateFlight(id, flight);
            return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Flight> updateFlightStatus(@PathVariable Long id,
                                                     @RequestParam String status) {
        try {
            Flight updatedFlight = flightService.updateFlightStatus(id, status);
            return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        try {
            flightService.deleteFlight(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

