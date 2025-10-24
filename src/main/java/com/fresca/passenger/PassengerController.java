package com.fresca.passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(
            @RequestBody Passenger passenger, @RequestParam Long cityId) {
        Passenger createdPassenger = passengerService.createPassenger(passenger, cityId);
        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers(){
        List<Passenger> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }





}
