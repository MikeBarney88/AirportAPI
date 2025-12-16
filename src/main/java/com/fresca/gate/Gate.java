package com.fresca.gate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fresca.airport.Airport;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "gates")
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gateNumber;

    @Column(nullable = false)
    private String terminal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GateStatus status;

    @ManyToOne
    @JsonIgnore
    private Airport airport;

    @Column
    private String currentFlight;

    public Gate() {
    }

    public Gate(String gateNumber, String terminal, GateStatus status, Airport airport) {
        this.gateNumber = gateNumber;
        this.terminal = terminal;
        this.status = status;
        this.airport = airport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public GateStatus getStatus() {
        return status;
    }

    public void setStatus(GateStatus status) {
        this.status = status;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public String getCurrentFlight() {
        return currentFlight;
    }

    public void setCurrentFlight(String currentFlight) {
        this.currentFlight = currentFlight;
    }

    public enum GateStatus {
        AVAILABLE,
        OCCUPIED,
        MAINTENANCE,
        CLOSED;


        @JsonCreator
        public static GateStatus fromString(String value) {
            return GateStatus.valueOf(value.toUpperCase());
        }
    }
}
