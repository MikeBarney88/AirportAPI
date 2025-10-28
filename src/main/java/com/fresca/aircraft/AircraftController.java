package com.fresca.aircraft;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final AircraftService service;


    public AircraftController(AircraftService service) {
        this.service = service;
    }

    // create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aircraft create(@RequestBody Aircraft body) {
        return service.create(body);
    }

    // list
    @GetMapping
    public List<Aircraft> list() {
        return service.findAll();
    }

    // get
    @GetMapping("/{id}")
    public Aircraft get(@PathVariable Long id) {
        return service.findById(id);
    }

    // update
    @PutMapping("/{id}")
    public Aircraft update(@PathVariable Long id, @RequestBody Aircraft body) {
        return service.update(id, body);
    }

    // delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // link passenger
    @PostMapping("/{aircraftId}/passengers/{passengerId}")
    public Aircraft addPassenger(@PathVariable Long aircraftId, @PathVariable Long passengerId) {
        return service.linkPassenger(aircraftId, passengerId);
    }

    // unlink passenger
    @DeleteMapping("/{aircraftId}/passengers/{passengerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePassenger(@PathVariable Long aircraftId, @PathVariable Long passengerId) {
        service.unlinkPassenger(aircraftId, passengerId);
    }

    // link airport
    @PostMapping("/{aircraftId}/airports/{airportId}")
    public Aircraft addAirport(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        return service.linkAirport(aircraftId, airportId);
    }

    // unlink airport
    @DeleteMapping("/{aircraftId}/airports/{airportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAirport(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        service.unlinkAirport(aircraftId, airportId);
    }
}

