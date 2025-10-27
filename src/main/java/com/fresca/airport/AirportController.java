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

    @GetMapping("/with-city")
    public ResponseEntity<List<Airport>> getAllAirportsWithCity() {
        List<Airport> airports = airportService.getAllAirportsWithCity();
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
}