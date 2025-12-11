package com.fresca.airport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {
    Optional<Gate> findByGateNumber(String gateNumber);
    List<Gate> findByAirportId(Long airportId);
    List<Gate> findByStatus(Gate.GateStatus status);
    List<Gate> findByAirportIdAndStatus(Long airportId, Gate.GateStatus status);
}
