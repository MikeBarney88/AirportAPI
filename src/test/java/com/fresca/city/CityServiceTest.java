package com.fresca.city;

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
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    CityRepository cityRepository;

    @InjectMocks
    CityService cityService;


    @Test
    void shouldCreateCityAndReturnSaved() {
        City input = new City();
        input.setName("St. John's");
        input.setState("Newfoundland and Labrador");
        input.setPopulation(110000);

        City saved = new City();
        saved.setId(1L);
        saved.setName("St. John's");
        saved.setState("Newfoundland and Labrador");
        saved.setPopulation(110000);

        when(cityRepository.save(any(City.class))).thenReturn(saved);

        City result = cityService.postCreateCity(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("St. John's", result.getName());
        assertEquals("Newfoundland and Labrador", result.getState());
        assertEquals(110000, result.getPopulation());
        verify(cityRepository).save(any(City.class));
    }


    @Test
    void shouldFindAllCities() {
        City city1 = new City();
        city1.setId(1L);
        city1.setName("St. John's");
        city1.setState("Newfoundland and Labrador");
        city1.setPopulation(110000);

        City city2 = new City();
        city2.setId(2L);
        city2.setName("Toronto");
        city2.setState("Ontario");
        city2.setPopulation(2930000);

        List<City> cityList = List.of(city1, city2);

        when(cityRepository.findAll()).thenReturn(cityList);

        List<City> result = cityService.getAllCities();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("St. John's", result.get(0).getName());
        assertEquals("Toronto", result.get(1).getName());
        verify(cityRepository).findAll();
    }


    @Test
    void shouldFindCityById() {
        City city = new City();
        city.setId(1L);
        city.setName("St. John's");
        city.setState("Newfoundland and Labrador");
        city.setPopulation(110000);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        City result = cityService.getCityById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("St. John's", result.getName());
        verify(cityRepository).findById(1L);
    }


    @Test
    void shouldReturnNullWhenCityNotFound() {
        when(cityRepository.findById(999L)).thenReturn(Optional.empty());

        City result = cityService.getCityById(999L);

        assertNull(result);
        verify(cityRepository).findById(999L);
    }


    @Test
    void shouldFindCitiesByState() {
        City city1 = new City();
        city1.setId(1L);
        city1.setName("St. John's");
        city1.setState("Newfoundland and Labrador");

        City city2 = new City();
        city2.setId(2L);
        city2.setName("Corner Brook");
        city2.setState("Newfoundland and Labrador");

        List<City> cityList = List.of(city1, city2);

        when(cityRepository.findByState("Newfoundland and Labrador")).thenReturn(cityList);

        List<City> result = cityService.getCitiesByState("Newfoundland and Labrador");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("St. John's", result.get(0).getName());
        assertEquals("Corner Brook", result.get(1).getName());
        verify(cityRepository).findByState("Newfoundland and Labrador");
    }


    @Test
    void shouldFindCityByName() {
        City city = new City();
        city.setId(1L);
        city.setName("St. John's");
        city.setState("Newfoundland and Labrador");

        when(cityRepository.findByName("St. John's")).thenReturn(city);

        City result = cityService.getCityByName("St. John's");

        assertNotNull(result);
        assertEquals("St. John's", result.getName());
        verify(cityRepository).findByName("St. John's");
    }


    @Test
    void shouldUpdateCity() {
        City existing = new City();
        existing.setId(1L);
        existing.setName("St. John's");
        existing.setState("Newfoundland and Labrador");
        existing.setPopulation(110000);

        City updateData = new City();
        updateData.setName("St. John's");
        updateData.setState("Newfoundland and Labrador");
        updateData.setPopulation(115000);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(cityRepository.save(any(City.class))).thenReturn(existing);

        City result = cityService.putUpdateCity(1L, updateData);

        assertNotNull(result);
        assertEquals(115000, existing.getPopulation());
        verify(cityRepository).findById(1L);
        verify(cityRepository).save(existing);
    }


    @Test
    void shouldReturnNullWhenUpdatingNonExistentCity() {
        City updateData = new City();
        updateData.setName("St. John's");

        when(cityRepository.findById(999L)).thenReturn(Optional.empty());

        City result = cityService.putUpdateCity(999L, updateData);

        assertNull(result);
        verify(cityRepository).findById(999L);
        verify(cityRepository, never()).save(any(City.class));
    }


    @Test
    void shouldDeleteCityById() {
        cityService.deleteCityById(1L);

        verify(cityRepository).deleteById(1L);
    }
}






















