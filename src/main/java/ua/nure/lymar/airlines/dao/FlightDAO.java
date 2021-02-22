package ua.nure.lymar.airlines.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ua.nure.lymar.airlines.entity.Flight;

/**
 * Provides an abstract interface to all type of DB for Flight Entity
 *
 * @see DaoException
 * @see DAO
 */
public interface FlightDAO extends DAO<Flight> {
    /**
     * Provides access to the list of all crews stored in db
     *
     * @return List of all crews stored in db
     * @throws DaoException
     */
    List<Flight> readAllFlights() throws DaoException;

    /**
     * Provides access to the list of flights with "new" status stored in db
     *
     * @return list of flights with "new" status
     * @throws DaoException
     */
    List<Flight> readNewFlights() throws DaoException;

    /**
     * Provides access to the list of flights stored in db, that not in archive
     *
     * @return list of flights, that not in archive
     * @throws DaoException
     */
    List<Flight> readActualFlights() throws DaoException;

    /**
     *  Search all flights with flightNumber
     * @param flightNumber - number of flight to search
     * @return
     * @throws DaoException
     */
    List<Flight> readFlihghtsByNumber(String flightNumber) throws DaoException;

    /**
     * Search flights by destination and departure
     * @param departure
     * @param arrival
     * @return
     * @throws DaoException
     */
    List<Flight> readFlightsByDepartureAndArrival(String departure, String arrival) throws DaoException;

    /**
     *
     * @return
     * @throws DaoException
     */
    List<String> selectAllDestinations() throws DaoException;

    /**
     *
     * @return
     * @throws DaoException
     */
    List<String> selectAllDepartures() throws DaoException;


    /**
     *
     * @param flightDate
     * @return
     * @throws DaoException
     */
    List<Flight> readFlihghtsByDate(Date flightDate) throws DaoException;


    /**
     *
     * @param flight
     * @return
     * @throws DaoException
     */
    List<Flight> readFlihghtsByObject(Flight flight) throws DaoException;
}
