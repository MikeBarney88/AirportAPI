package com.fresca.passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByCityId(Long cityId);
    List<Passenger> findByLastName(String lastName);

    @Query("SELECT passenger FROM Passenger passenger JOIN FETCH passenger.city")
    List<Passenger> findAllWithCity();

    @Query("SELECT DISTINCT passenger FROM Passenger passenger LEFT JOIN FETCH passenger.aircraft WHERE passenger.id = :id")
    Optional<Passenger> findByIdWithAircraft(@Param("id") Long id);
}