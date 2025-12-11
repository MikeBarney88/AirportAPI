package com.fresca.airport;

import com.fresca.city.City;
import com.fresca.city.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GateRepository gateRepository;

    public Airport createAirport(Airport airport, Long CityId) {
        Optional<City> cityOpt = cityRepository.findById(CityId);
        if (cityOpt.isEmpty()) {
            throw new IllegalArgumentException("City not found with id: " + CityId);
        }
        City city = cityOpt.get();
        airport.setCity(city);
        return airportRepository.save(airport);
    }

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Optional<Airport> getAirportById(Long id) {
        return airportRepository.findById(id);
    }

    public List<Airport> getAirportsByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId);
    }

    public Airport updateAirport(Long id, Airport airportDetails) {
        Optional<Airport> airportOpt = airportRepository.findById(id);
        if (airportOpt.isEmpty()) {
            throw new IllegalArgumentException("Airport not found with id: " + id);
        }
        Airport airport = airportOpt.get();

        airport.setName(airportDetails.getName());
        airport.setCode(airportDetails.getCode());

        return airportRepository.save(airport);
    }

    public void deleteAirport(Long id) {
        Optional<Airport> airportOpt = airportRepository.findById(id);
        if (airportOpt.isEmpty()) {
            throw new IllegalArgumentException("Airport not found with id: " + id);
        }
        Airport airport = airportOpt.get();
        airportRepository.delete(airport);
    }
    // Gate Management Methods

    public Gate createGate(Gate gate, Long airportId) {
        Optional<Airport> airportOpt = airportRepository.findById(airportId);
        if (airportOpt.isEmpty()) {
            throw new IllegalArgumentException("Airport not found with id: " + airportId);
        }
        Airport airport = airportOpt.get();
        gate.setAirport(airport);

        return gateRepository.save(gate);
    }

    public List<Gate> getGatesByAirportId(Long airportId) {
        return gateRepository.findByAirportId(airportId);
    }

    public Optional<Gate> getGateByGateNumber(String gateNumber) {
        return gateRepository.findByGateNumber(gateNumber);
    }

    public List<Gate> getAvailableGatesByAirport(Long airportId) {
        return gateRepository.findByAirportIdAndStatus(airportId, Gate.GateStatus.AVAILABLE);
    }

    public Gate updateGateStatus(String gateNumber, Gate.GateStatus newStatus) {
        Optional<Gate> gateOpt = gateRepository.findByGateNumber(gateNumber);
        if (gateOpt.isEmpty()) {
            throw new IllegalArgumentException("Gate not found: " + gateNumber);
        }

        Gate gate = gateOpt.get();
        gate.setStatus(newStatus);

        return gateRepository.save(gate);
    }

    public Gate updateGate(String gateNumber, Gate gateDetails) {
        Optional<Gate> gateOpt = gateRepository.findByGateNumber(gateNumber);
        if (gateOpt.isEmpty()) {
            throw new IllegalArgumentException("Gate not found: " + gateNumber);
        }

        Gate gate = gateOpt.get();
        gate.setGateNumber(gateDetails.getGateNumber());
        gate.setTerminal(gateDetails.getTerminal());
        gate.setStatus(gateDetails.getStatus());
        gate.setCurrentFlight(gateDetails.getCurrentFlight());

        return gateRepository.save(gate);
    }

    public void deleteGate(String gateNumber) {
        Optional<Gate> gateOpt = gateRepository.findByGateNumber(gateNumber);
        if (gateOpt.isEmpty()) {
            throw new IllegalArgumentException("Gate not found: " + gateNumber);
        }

        gateRepository.delete(gateOpt.get());
    }
}

