package com.fresca.aircraft;

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
public class AircraftServiceTest {

    @Mock
    AircraftRepository repo;

    @InjectMocks
    AircraftService aircraftService;


    @Test
    void shouldCreateAircraftAndReturnSaved() {
        Aircraft input = new Aircraft();
        input.setType("Boeing 737");
        input.setAirlineName("Air Canada");
        input.setNumberOfPassengers(180);

        Aircraft saved = new Aircraft();
        saved.setId(1L);
        saved.setType("Boeing 737");
        saved.setAirlineName("Air Canada");
        saved.setNumberOfPassengers(180);

        when(repo.save(any(Aircraft.class))).thenReturn(saved);

        Aircraft result = aircraftService.create(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Boeing 737", result.getType());
        assertEquals("Air Canada", result.getAirlineName());
        assertEquals(180, result.getNumberOfPassengers());
        verify(repo).save(any(Aircraft.class));
    }


    @Test
    void shouldFindAllAircraft() {
        Aircraft aircraft1 = new Aircraft();
        aircraft1.setId(1L);
        aircraft1.setType("Boeing 737");
        aircraft1.setAirlineName("Air Canada");

        Aircraft aircraft2 = new Aircraft();
        aircraft2.setId(2L);
        aircraft2.setType("Airbus A320");
        aircraft2.setAirlineName("WestJet");

        List<Aircraft> aircraftList = List.of(aircraft1, aircraft2);

        when(repo.findAll()).thenReturn(aircraftList);

        List<Aircraft> result = aircraftService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Boeing 737", result.get(0).getType());
        assertEquals("Airbus A320", result.get(1).getType());
        verify(repo).findAll();
    }


    @Test
    void shouldFindAircraftById() {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);
        aircraft.setType("Boeing 737");
        aircraft.setAirlineName("Air Canada");
        aircraft.setNumberOfPassengers(180);

        when(repo.findById(1L)).thenReturn(Optional.of(aircraft));

        Aircraft result = aircraftService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Boeing 737", result.getType());
        assertEquals("Air Canada", result.getAirlineName());
        verify(repo).findById(1L);
    }


    @Test
    void shouldThrowExceptionWhenAircraftNotFound() {
        when(repo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            aircraftService.findById(999L);
        });

        verify(repo).findById(999L);
    }


    @Test
    void shouldUpdateAircraft() {
        Aircraft existing = new Aircraft();
        existing.setId(1L);
        existing.setType("Boeing 737");
        existing.setAirlineName("Air Canada");
        existing.setNumberOfPassengers(180);

        Aircraft updateData = new Aircraft();
        updateData.setType("Boeing 747");
        updateData.setAirlineName("Air Canada");
        updateData.setNumberOfPassengers(200);

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Aircraft.class))).thenReturn(existing);

        Aircraft result = aircraftService.update(1L, updateData);

        assertNotNull(result);
        assertEquals("Boeing 747", existing.getType());
        assertEquals(200, existing.getNumberOfPassengers());
        verify(repo).findById(1L);
        verify(repo).save(existing);
    }


    @Test
    void shouldDeleteAircraft() {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);
        aircraft.setType("Boeing 737");

        when(repo.findById(1L)).thenReturn(Optional.of(aircraft));

        aircraftService.delete(1L);

        verify(repo).findById(1L);
        verify(repo).delete(aircraft);
    }
}