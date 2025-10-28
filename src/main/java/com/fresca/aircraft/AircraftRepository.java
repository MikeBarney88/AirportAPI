package com.fresca.aircraft;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    //repo inherits CRUD
}

