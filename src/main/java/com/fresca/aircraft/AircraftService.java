package com.fresca.aircraft;

import com.fresca.airport.Airport;
import com.fresca.passenger.Passenger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Spring's Tx (works best with Spring)

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

    public Aircraft create(Aircraft body) {
        var toSave = new Aircraft(body.getType(), body.getAirlineName(), body.getNumberOfPassengers());
        return repo.save(toSave);
    }

    @Transactional(readOnly = true)
    public List<Aircraft> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Aircraft findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Aircraft " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Aircraft findByIdWithAirports(Long id) {
        Aircraft aircraft = findById(id);
        aircraft.getAirports().size();
        return aircraft;
    }

    public Aircraft update(Long id, Aircraft body) {
        var existing = findById(id);
        existing.setType(body.getType());
        existing.setAirlineName(body.getAirlineName());
        existing.setNumberOfPassengers(body.getNumberOfPassengers());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.delete(findById(id));
    }

    public Aircraft linkPassenger(Long aircraftId, Long passengerId) {
        var aircraft = findById(aircraftId);
        var passengerRef = em.getReference(Passenger.class, passengerId);
        aircraft.addPassenger(passengerRef);
        return repo.save(aircraft);
    }

    public void unlinkPassenger(Long aircraftId, Long passengerId) {
        var aircraft = findById(aircraftId);
        var passengerRef = em.getReference(Passenger.class, passengerId);
        aircraft.removePassenger(passengerRef);
        repo.save(aircraft);
    }

    public Aircraft linkAirport(Long aircraftId, Long airportId) {
        var aircraft = findById(aircraftId);
        var airportRef = em.getReference(Airport.class, airportId);
        aircraft.addAirport(airportRef);
        return repo.save(aircraft);
    }

    public void unlinkAirport(Long aircraftId, Long airportId) {
        var aircraft = findById(aircraftId);
        var airportRef = em.getReference(Airport.class, airportId);
        aircraft.removeAirport(airportRef);
        repo.save(aircraft);
    }
}
