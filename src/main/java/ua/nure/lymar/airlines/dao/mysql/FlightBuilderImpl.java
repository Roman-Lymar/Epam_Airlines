package ua.nure.lymar.airlines.dao.mysql;

import ua.nure.lymar.airlines.dao.FlightQueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightBuilderImpl implements FlightQueryBuilder {

    @Override
    public String addDestinationCity(String destinationCity) {
        return "and flight_destinationCity like '%" + destinationCity + "%'";
    }

    @Override
    public String addDepartureCity(String departureCity) {
        return "and flight_departureCity like '%" + departureCity + "%'";
    }

    @Override
    public String addFlightDate(Date flightDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String dateStr = simpleDateFormat.format(flightDate);
        return "and flight_date = '" + dateStr +"'";
    }

    @Override
    public String addFlightName(String name) {
        return "and flight_name like '%" + name + "%'";
    }
}
