package com.fresca.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport, @RequestParam Long cityId) {
        Airport createdAirport = airportService.createAirport(airport, cityId);
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        Optional<Airport> airportOpt = airportService.getAirportById(id);
        if (airportOpt.isPresent()) {
            return new ResponseEntity<>(airportOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Airport>> getAirportsByCity(@PathVariable Long cityId) {
        List<Airport> airports = airportService.getAirportsByCityId(cityId);
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airport) {
        try {
            Airport updatedAirport = airportService.updateAirport(id, airport);
            return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        try {
            airportService.deleteAirport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Gate Management Endpoints

    @PostMapping("/{airportId}/gates")
    public ResponseEntity<Gate> createGate(@PathVariable Long airportId, @RequestBody Gate gate) {
        try {
            Gate createdGate = airportService.createGate(gate, airportId);
            return new ResponseEntity<>(createdGate, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{airportId}/gates")
    public ResponseEntity<List<Gate>> getAirportGates(@PathVariable Long airportId) {
        List<Gate> gates = airportService.getGatesByAirportId(airportId);
        return new ResponseEntity<>(gates, HttpStatus.OK);
    }

    @GetMapping("/{airportId}/gates/available")
    public ResponseEntity<List<Gate>> getAvailableGates(@PathVariable Long airportId) {
        List<Gate> gates = airportService.getAvailableGatesByAirport(airportId);
        return new ResponseEntity<>(gates, HttpStatus.OK);
    }

    @GetMapping("/gates/{gateNumber}")
    public ResponseEntity<Gate> getGateByNumber(@PathVariable String gateNumber) {
        Optional<Gate> gateOpt = airportService.getGateByGateNumber(gateNumber);
        return gateOpt.map(gate -> new ResponseEntity<>(gate, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/gates/{gateNumber}/status")
    public ResponseEntity<Gate> updateGateStatus(
            @PathVariable String gateNumber,
            @RequestParam Gate.GateStatus status) {
        try {
            Gate updatedGate = airportService.updateGateStatus(gateNumber, status);
            return new ResponseEntity<>(updatedGate, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/gates/{gateNumber}")
    public ResponseEntity<Gate> updateGate(
            @PathVariable String gateNumber,
            @RequestBody Gate gate) {
        try {
            Gate updatedGate = airportService.updateGate(gateNumber, gate);
            return new ResponseEntity<>(updatedGate, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/gates/{gateNumber}")
    public ResponseEntity<Void> deleteGate(@PathVariable String gateNumber) {
        try {
            airportService.deleteGate(gateNumber);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}