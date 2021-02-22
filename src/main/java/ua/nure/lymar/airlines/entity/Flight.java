package ua.nure.lymar.airlines.entity;

import java.util.Date;
import java.sql.Time;

/**
 * JavaBean class of Flight entity
 */
public class Flight extends Entity {
    private String flightName;
    private String departureCity;
    private String destinationCity;
    private FlightStatus status;
    private Crew crew;
    private Date date;
    private Time time;

    public String getName() {
        return flightName;
    }

    public void setName(String name) {
        this.flightName = name;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    @Override
    public String toString(){
     return getName() + ", " + getDepartureCity() + "," + getDestinationCity() + "," + getTime();
    }
}
