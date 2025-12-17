package com.fresca.airport;

import com.fresca.city.City;
import com.fresca.city.CityRepository;
import com.fresca.gate.GateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @Mock
    AirportRepository airportRepository;

    @Mock
    CityRepository cityRepository;

    @Mock
    GateRepository gateRepository;

    @InjectMocks
    AirportService airportService;


    @Test
    void shouldCreateAirportAndReturnSaved() {
        Airport input = new Airport();
        input.setName("St. John's International Airport");
        input.setCode("YYT");

        City city = new City();
        city.setId(1L);
        city.setName("St. John's");

        Airport saved = new Airport();
        saved.setId(1L);
        saved.setName("St. John's International Airport");
        saved.setCode("YYT");
        saved.setCity(city);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(airportRepository.save(any(Airport.class))).thenReturn(saved);

        Airport result = airportService.createAirport(input, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("St. John's International Airport", result.getName());
        assertEquals("YYT", result.getCode());
        verify(cityRepository).findById(1L);
        verify(airportRepository).save(any(Airport.class));
    }


    @Test
    void shouldFindAllAirports() {
        Airport airport1 = new Airport();
        airport1.setId(1L);
        airport1.setName("St. John's International Airport");
        airport1.setCode("YYT");

        Airport airport2 = new Airport();
        airport2.setId(2L);
        airport2.setName("Toronto Pearson International Airport");
        airport2.setCode("YYZ");

        List<Airport> airportList = List.of(airport1, airport2);

        when(airportRepository.findAll()).thenReturn(airportList);

        List<Airport> result = airportService.getAllAirports();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("YYT", result.get(0).getCode());
        assertEquals("YYZ", result.get(1).getCode());
        verify(airportRepository).findAll();
    }


    @Test
    void shouldFindAirportById() {
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setName("St. John's International Airport");
        airport.setCode("YYT");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        Optional<Airport> result = airportService.getAirportById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("YYT", result.get().getCode());
        verify(airportRepository).findById(1L);
    }


    @Test
    void shouldReturnEmptyWhenAirportNotFound() {
        when(airportRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Airport> result = airportService.getAirportById(999L);

        assertFalse(result.isPresent());
        verify(airportRepository).findById(999L);
    }


    @Test
    void shouldUpdateAirport() {
        Airport existing = new Airport();
        existing.setId(1L);
        existing.setName("St. John's International Airport");
        existing.setCode("YYT");

        Airport updateData = new Airport();
        updateData.setName("St. John's International Airport - Updated");
        updateData.setCode("YYT");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(airportRepository.save(any(Airport.class))).thenReturn(existing);

        Airport result = airportService.updateAirport(1L, updateData);

        assertNotNull(result);
        assertEquals("St. John's International Airport - Updated", existing.getName());
        verify(airportRepository).findById(1L);
        verify(airportRepository).save(existing);
    }


    @Test
    void shouldDeleteAirport() {
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setName("St. John's International Airport");
        airport.setCode("YYT");

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        airportService.deleteAirport(1L);

        verify(airportRepository).findById(1L);
        verify(airportRepository).delete(airport);
    }
}
