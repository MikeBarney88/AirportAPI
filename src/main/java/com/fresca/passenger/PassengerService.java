package com.fresca.passenger;

import com.fresca.airport.Airport;
import com.fresca.city.City;
import com.fresca.city.CityRepository;
import com.fresca.aircraft.Aircraft;
import com.fresca.aircraft.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    public Passenger createPassenger(Passenger passenger, Long cityId) {
        Optional<City> cityOpt = cityRepository.findById(cityId);
        if (cityOpt.isEmpty()) {
            throw new IllegalArgumentException("City not found with id: " + cityId);
        }
        City city = cityOpt.get();
        passenger.setCity(city);
        return passengerRepository.save(passenger);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepository.findById(id);
    }

    public Optional<Passenger> getPassengerWithAircraft(Long id) {
        return passengerRepository.findByIdWithAircraft(id);
    }

    public Passenger updatePassenger(Long id, Passenger passengerDetails) {
        Optional<Passenger> passengerOpt = passengerRepository.findById(id);
        if (passengerOpt.isEmpty()) {
            throw new IllegalArgumentException("Passenger not found with id: " + id);
        }
        Passenger passenger = passengerOpt.get();

        passenger.setFirstName(passengerDetails.getFirstName());
        passenger.setLastName(passengerDetails.getLastName());
        passenger.setPhoneNumber(passengerDetails.getPhoneNumber());

        return passengerRepository.save(passenger);
    }

    public void deletePassenger(Long id) {
        Optional<Passenger> passengerOpt = passengerRepository.findById(id);
        if (passengerOpt.isEmpty()) {
            throw new IllegalArgumentException("Passenger not found with id: " + id);
        }
        Passenger passenger = passengerOpt.get();
        passengerRepository.delete(passenger);
    }

    public Passenger addPassengerToAircraft(Long passengerId, Long aircraftId) {
        Optional<Passenger> passengerOpt = passengerRepository.findById(passengerId);
        if (passengerOpt.isEmpty()) {
            throw new IllegalArgumentException("Passenger not found with id: " + passengerId);
        }
        Passenger passenger = passengerOpt.get();

        Optional<Aircraft> aircraftOpt = aircraftRepository.findById(aircraftId);
        if (aircraftOpt.isEmpty()){
            throw new IllegalArgumentException("Aircraft not found with id: " + aircraftId);
        }
        Aircraft aircraft = aircraftOpt.get();

        passenger.getAircraft().add(aircraft);
        return passengerRepository.save(passenger);
    }

    public List<Airport> getAllPassengerAirports() {
        return passengerRepository.findAllPassengerAirports();
    }
}
