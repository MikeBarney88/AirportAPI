package com.fresca.passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger,
                                                     @RequestParam Long cityId) {
        Passenger createdPassenger = passengerService.createPassenger(passenger, cityId);
        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        Optional<Passenger> passengerOpt = passengerService.getPassengerById(id);
        if (passengerOpt.isPresent()) {
            return new ResponseEntity<>(passengerOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/aircraft")
    public ResponseEntity<Passenger> getPassengerWithAircraft(@PathVariable Long id) {
        Optional<Passenger> passengerOpt = passengerService.getPassengerWithAircraft(id);
        if (passengerOpt.isPresent()) {
            return new ResponseEntity<>(passengerOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id,
                                                     @RequestBody Passenger passenger) {
        try {
            Passenger updatedPassenger = passengerService.updatePassenger(id, passenger);
            return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{passengerId}/aircraft/{aircraftId}")
    public ResponseEntity<Passenger> addPassengerToAircraft(@PathVariable Long passengerId,
                                                            @PathVariable Long aircraftId) {
        try {
            Passenger passenger = passengerService.addPassengerToAircraft(passengerId, aircraftId);
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        try {
            passengerService.deletePassenger(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
