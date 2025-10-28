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

    public List<Airport> getAllAirportsWithCity() {
        return airportRepository.findAllWithCity();
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
}
