package com.fresca.city;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    //Select Queries
    City findByName(String name);

    List<City> findByState(String state);
}