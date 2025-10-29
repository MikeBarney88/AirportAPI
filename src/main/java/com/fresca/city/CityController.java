package com.fresca.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CityController {
    @Autowired
    private CityService cityService;


    //GET Routes
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/cities/{id}")
    public City getCityById(@PathVariable long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/city-search")
    public List<City> searchCities(@RequestParam(value = "name", required = false) String cityName,
                                   @RequestParam(value = "state", required = false) String cityState) {
        ArrayList<City> searchResults = new ArrayList<>();

        if (cityName != null && cityState == null) {
            City cityFound = cityService.getCityByName(cityName);

            searchResults.add(cityFound);
        } else if (cityState != null && cityName == null) {
            List<City> citiesFound = cityService.getCitiesByState(cityState);

            searchResults.addAll(citiesFound);
        }

        return searchResults;
    }

    //POST Route
    @PostMapping("/cities")
    public City postCreateCity(@RequestBody City city) {
        return cityService.postCreateCity(city);
    }


    //PUT Route
    @PutMapping("/city/{id}")
    public ResponseEntity<City> putUpdateCity(@PathVariable long id, @RequestBody City city) {
        return ResponseEntity.ok(cityService.putUpdateCity(id, city));
    }


    //DELETE Route
    @DeleteMapping("/city/{id}")
    public void deleteCityById(@PathVariable long id) {
        cityService.deleteCityById(id);
    }
}