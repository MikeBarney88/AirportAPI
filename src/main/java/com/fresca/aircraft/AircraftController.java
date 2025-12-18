package com.fresca.aircraft;

import com.fresca.airport.Airport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aircrafts")
@CrossOrigin(origins = "http://fresca-airport-frontend.s3-website-us-east-1.amazonaws.com/")
public class AircraftController {

    private final AircraftService service;


    public AircraftController(AircraftService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aircraft create(@RequestBody Aircraft body) {
        return service.create(body);
    }

    @GetMapping
    public List<Aircraft> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Aircraft get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Aircraft update(@PathVariable Long id, @RequestBody Aircraft body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{aircraftId}/passengers/{passengerId}")
    public Aircraft addPassenger(@PathVariable Long aircraftId, @PathVariable Long passengerId) {
        return service.linkPassenger(aircraftId, passengerId);
    }

    @DeleteMapping("/{aircraftId}/passengers/{passengerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePassenger(@PathVariable Long aircraftId, @PathVariable Long passengerId) {
        service.unlinkPassenger(aircraftId, passengerId);
    }

    @PostMapping("/{aircraftId}/airports/{airportId}")
    public Aircraft addAirport(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        return service.linkAirport(aircraftId, airportId);
    }

    @GetMapping("/{id}/airports")
    public List<Airport> getAirports(@PathVariable Long id) {
        Aircraft aircraft = service.findById(id);
        return new ArrayList<>(aircraft.getAirports());
    }

    @DeleteMapping("/{aircraftId}/airports/{airportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAirport(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        service.unlinkAirport(aircraftId, airportId);
    }
}

