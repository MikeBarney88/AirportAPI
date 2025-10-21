package com.fresca.city;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class City {
    @Id
    @SequenceGenerator(name = "city_sequence", sequenceName = "city_sequence", allocationSize = 1)
    @GeneratedValue(generator = "city_sequence")
    private long id;

    private String name, state;
    private double population;


    //Constructor(s)
    public City(String name, String state, double population) {
        this.name = name;
        this.state = state;
        this.population = population;
    }


    //Instance Methods
    public String toString() {
        return String.format("City[id = %d, name = \"%s\", state = \"%s\", population = %,.0f]", this.id, this.name, this.state, this.population);
    }


    //Getter Methods
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getState() {
        return this.state;
    }

    public double getPopulation() {
        return this.population;
    }


    //Setter Methods
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPopulation(double population) {
        this.population = population;
    }
}