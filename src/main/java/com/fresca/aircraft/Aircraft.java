package com.fresca.aircraft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fresca.passenger.Passenger;
import com.fresca.airport.Airport;
import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  private String type;
    @Column(nullable = false)  private String airlineName;
    @Column(nullable = false)  private int numberOfPassengers;

    @ManyToMany
    @JoinTable(
            name = "aircraft_passengers",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    @JsonIgnore
    private Set<Passenger> passengers = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "aircraft_airports",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    @JsonIgnore
    private Set<Airport> airports = new LinkedHashSet<>();

    public Aircraft() {}

    public Aircraft(String type, String airlineName, int numberOfPassengers) {
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(long l) {  this.id = l; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
    public int getNumberOfPassengers() { return numberOfPassengers; }
    public void setNumberOfPassengers(int numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }

    public Set<Passenger> getPassengers() { return passengers; }
    public Set<Airport> getAirports() { return airports; }

    public void addPassenger(Passenger p) { passengers.add(p); }
    public void removePassenger(Passenger p) { passengers.remove(p); }
    public void addAirport(Airport a) { airports.add(a); }
    public void removeAirport(Airport a) { airports.remove(a); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aircraft)) return false;
        Aircraft aircraft = (Aircraft) o;
        return id != null && Objects.equals(id, aircraft.id);
    }

    @Override public int hashCode() { return getClass().hashCode(); }
}
