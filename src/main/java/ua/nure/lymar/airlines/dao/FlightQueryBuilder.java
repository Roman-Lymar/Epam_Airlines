package ua.nure.lymar.airlines.dao;

import java.util.Date;

public interface FlightQueryBuilder {

    String addDestinationCity(String destinationCity);

    String addDepartureCity(String departureCity);

    String addFlightDate(Date flightDate);

    String addFlightName(String name);

}
