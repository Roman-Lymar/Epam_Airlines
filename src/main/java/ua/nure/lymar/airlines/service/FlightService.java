package ua.nure.lymar.airlines.service;

import java.util.Date;
import java.util.List;

import ua.nure.lymar.airlines.dao.FlightDAO;
import ua.nure.lymar.airlines.entity.Flight;

public interface FlightService {
    Flight readById(Integer id) throws ServiceException;

    List<Flight> readFlights() throws ServiceException;

    List<Flight> readActualFlights() throws ServiceException;

    List<Flight> readNewFlights() throws ServiceException;

    void save(Flight flight) throws ServiceException;

    void delete(Integer id) throws ServiceException;

    void sortByName(List<Flight> flights);

    void sortByDate(List<Flight> flights);

    void sortByDepartureCity(List<Flight> flights);

    void sortByArrivalCity(List<Flight> flights);

    List<Flight> readFlightsByNumber(String flightNumber) throws ServiceException;

    List<String> selectAllDepartureCities() throws ServiceException;

    List<String> selectAllDestinationCities() throws ServiceException;

    List<Flight> searchByFlight(Flight flight) throws ServiceException;

	List<Flight> readFlightsByDate(Date flightDate) throws ServiceException;

	List<Flight> readFlightsByMultipleParameters(Flight flight) throws ServiceException;

	void refreshFlightList(List<Flight> flights) throws ServiceException;

    void setFlightDAO(FlightDAO flightDAO);
}
