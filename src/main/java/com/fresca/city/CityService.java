package com.fresca.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;


    //GET Operations
    public List<City> getAllCities() {
        return (List<City>) cityRepository.findAll();
    }

    public City getCityById(long id) {
        Optional<City> cityOptional = cityRepository.findById(id);

        return cityOptional.orElse(null);
    }

    public List<City> getCitiesByState(String state) {
        return cityRepository.findByState(state);
    }

    public City getCityByName(String name) {
        return cityRepository.findByName(name);
    }


    //POST Operations
    public City postCreateCity(City newCity) {
        return cityRepository.save(newCity);
    }


    //PUT Operations
    public City putUpdateCity(long id, City updatedCity) {
        Optional<City> cityToUpdateOptional = cityRepository.findById(id);

        if (cityToUpdateOptional.isPresent()) {
            City cityToUpdate = cityToUpdateOptional.get();

            cityToUpdate.setName(updatedCity.getName());
            cityToUpdate.setState(updatedCity.getState());
            cityToUpdate.setPopulation(updatedCity.getPopulation());

            return cityRepository.save(cityToUpdate);
        }

        return null;
    }

    //DELETE Operations
    public void deleteCityById(long id) {
        cityRepository.deleteById(id);
    }
}