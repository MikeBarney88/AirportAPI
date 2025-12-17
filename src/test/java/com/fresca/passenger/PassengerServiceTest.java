package com.fresca.passenger;

import com.fresca.aircraft.Aircraft;
import com.fresca.aircraft.AircraftRepository;
import com.fresca.city.City;
import com.fresca.city.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    CityRepository cityRepository;

    @Mock
    AircraftRepository aircraftRepository;

    @InjectMocks
    PassengerService passengerService;


    @Test
    void shouldCreatePassengerAndReturnSaved() {
        Passenger input = new Passenger();
        input.setFirstName("John");
        input.setLastName("Smith");
        input.setPhoneNumber("555-1234");

        City city = new City();
        city.setId(1L);
        city.setName("St. John's");

        Passenger saved = new Passenger();
        saved.setId(1L);
        saved.setFirstName("John");
        saved.setLastName("Smith");
        saved.setPhoneNumber("555-1234");
        saved.setCity(city);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(saved);

        Passenger result = passengerService.createPassenger(input, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("555-1234", result.getPhoneNumber());
        verify(cityRepository).findById(1L);
        verify(passengerRepository).save(any(Passenger.class));
    }


    @Test
    void shouldThrowExceptionWhenCityNotFound() {
        Passenger input = new Passenger();
        input.setFirstName("John");
        input.setLastName("Smith");

        when(cityRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.createPassenger(input, 999L);
        });

        verify(cityRepository).findById(999L);
    }


    @Test
    void shouldFindAllPassengers() {
        Passenger passenger1 = new Passenger();
        passenger1.setId(1L);
        passenger1.setFirstName("John");
        passenger1.setLastName("Smith");
        passenger1.setPhoneNumber("555-1234");

        Passenger passenger2 = new Passenger();
        passenger2.setId(2L);
        passenger2.setFirstName("Jane");
        passenger2.setLastName("Doe");
        passenger2.setPhoneNumber("555-5678");

        List<Passenger> passengerList = List.of(passenger1, passenger2);

        when(passengerRepository.findAll()).thenReturn(passengerList);

        List<Passenger> result = passengerService.getAllPassengers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(passengerRepository).findAll();
    }


    @Test
    void shouldFindPassengerById() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setPhoneNumber("555-1234");

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        Optional<Passenger> result = passengerService.getPassengerById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Smith", result.get().getLastName());
        verify(passengerRepository).findById(1L);
    }


    @Test
    void shouldReturnEmptyWhenPassengerNotFound() {
        when(passengerRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Passenger> result = passengerService.getPassengerById(999L);

        assertFalse(result.isPresent());
        verify(passengerRepository).findById(999L);
    }


    @Test
    void shouldUpdatePassenger() {
        Passenger existing = new Passenger();
        existing.setId(1L);
        existing.setFirstName("John");
        existing.setLastName("Smith");
        existing.setPhoneNumber("555-1234");

        Passenger updateData = new Passenger();
        updateData.setFirstName("John");
        updateData.setLastName("Smith");
        updateData.setPhoneNumber("555-9999");

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(existing);

        Passenger result = passengerService.updatePassenger(1L, updateData);

        assertNotNull(result);
        assertEquals("555-9999", existing.getPhoneNumber());
        verify(passengerRepository).findById(1L);
        verify(passengerRepository).save(existing);
    }


    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentPassenger() {
        Passenger updateData = new Passenger();
        updateData.setFirstName("John");

        when(passengerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.updatePassenger(999L, updateData);
        });

        verify(passengerRepository).findById(999L);
    }


    @Test
    void shouldDeletePassenger() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setPhoneNumber("555-1234");

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        passengerService.deletePassenger(1L);

        verify(passengerRepository).findById(1L);
        verify(passengerRepository).delete(passenger);
    }


    @Test
    void shouldThrowExceptionWhenDeletingNonExistentPassenger() {
        when(passengerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.deletePassenger(999L);
        });

        verify(passengerRepository).findById(999L);
    }


    @Test
    void shouldAddPassengerToAircraft() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setAircraft(new ArrayList<>());

        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);
        aircraft.setType("Boeing 737");

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

        Passenger result = passengerService.addPassengerToAircraft(1L, 1L);

        assertNotNull(result);
        assertTrue(passenger.getAircraft().contains(aircraft));
        verify(passengerRepository).findById(1L);
        verify(aircraftRepository).findById(1L);
        verify(passengerRepository).save(passenger);
    }


    @Test
    void shouldThrowExceptionWhenAddingToNonExistentAircraft() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(aircraftRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.addPassengerToAircraft(1L, 999L);
        });

        verify(passengerRepository).findById(1L);
        verify(aircraftRepository).findById(999L);
    }
}