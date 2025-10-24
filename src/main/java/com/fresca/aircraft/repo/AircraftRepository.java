package com.fresca.aircraft.repo;

import com.fresca.aircraft.domain.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    //repo inherits CRUD
}

