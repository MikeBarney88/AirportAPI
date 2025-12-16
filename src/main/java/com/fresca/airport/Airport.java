package com.fresca.airport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fresca.aircraft.Aircraft;
import com.fresca.city.City;
import com.fresca.flight.Flight;
import com.fresca.gate.Gate;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airport")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, length = 3)
    private String code;

    @ManyToOne
    @JsonIgnore
    private City city;

    @ManyToMany(mappedBy = "airports")
    @JsonIgnore
    private List<Aircraft> aircraft = new ArrayList<>();

    @OneToMany(mappedBy = "airport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Gate> gates = new ArrayList<>();

    @OneToMany(mappedBy = "fromAirport")
    @JsonIgnore
    private List<Flight> departingFlights = new ArrayList<>();

    public Airport() {}

    public Airport(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Aircraft> getAircraft() {
        return aircraft;
    }

    public void setAircraft(List<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }
    public List<Gate> getGates() {
        return gates;
    }

    public void setGates(List<Gate> gates) {
        this.gates = gates;
    }

    public List<Flight> getDepartingFlights() {
        return departingFlights;
    }

    public void setDepartingFlights(List<Flight> departingFlights) {
        this.departingFlights = departingFlights;
    }
}
