package com.fresca.aircraft;

import com.fresca.airport.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    //repo inherits CRUD
    @Query("SELECT DISTINCT a FROM Passenger p JOIN p.aircraft ac JOIN ac.airports a")
    List<Airport> findAllPassengerAirports();
}

