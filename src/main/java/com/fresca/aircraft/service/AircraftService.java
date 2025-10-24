package com.fresca.aircraft.service;

import com.fresca.aircraft.domain.Aircraft;
import com.fresca.aircraft.repo.AircraftRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AircraftService {

    private final AircraftRepository repo;

    @PersistenceContext
    private EntityManager em;


    public AircraftService(AircraftRepository repo) {
        this.repo = repo;
    }

    // create aircraft
    public Aircraft create(Aircraft body) {
        var toSave = new Aircraft(body.getType(), body.getAirlineName(), body.getNumberOfPassengers());
        return repo.save(toSave);
    }

    // list aircraft
    public List<Aircraft> findAll() {
        return repo.findAll();
    }

    // get aircraft
    public Aircraft findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Aircraft " + id + " not found"));
    }

    // update aircraft
    public Aircraft update(Long id, Aircraft body) {
        var existing = findById(id);
        existing.setType(body.getType());
        existing.setAirlineName(body.getAirlineName());
        existing.setNumberOfPassengers(body.getNumberOfPassengers());
        return existing;
    }

    // delete aircraft
    public void delete(Long id) {
        var existing = findById(id);
        repo.delete(existing);
    }

    // link passenger
    public Aircraft linkPassenger(Long aircraftId, Long passengerId) {
        var aircraft = findById(aircraftId);
        var passengerRef = em.getReference(com.fresca.passenger.domain.Passenger.class, passengerId);
        aircraft.addPassenger(passengerRef);
        return aircraft;
    }

    // unlink passenger
    public void unlinkPassenger(Long aircraftId, Long passengerId) {
        var aircraft = findById(aircraftId);
        var passengerRef = em.getReference(com.fresca.passenger.domain.Passenger.class, passengerId);
        aircraft.removePassenger(passengerRef);
    }

    // link airport
    public Aircraft linkAirport(Long aircraftId, Long airportId) {
        var aircraft = findById(aircraftId);
        var airportRef = em.getReference(com.fresca.airport.domain.Airport.class, airportId);
        aircraft.addAirport(airportRef);
        return aircraft;
    }

    // unlink airport
    public void unlinkAirport(Long aircraftId, Long airportId) {
        var aircraft = findById(aircraftId);
        var airportRef = em.getReference(com.fresca.airport.domain.Airport.class, airportId);
        aircraft.removeAirport(airportRef);
    }
}

