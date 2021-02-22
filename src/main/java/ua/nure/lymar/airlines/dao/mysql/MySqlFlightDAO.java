package ua.nure.lymar.airlines.dao.mysql;

import org.apache.log4j.Logger;
import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.FlightDAO;
import ua.nure.lymar.airlines.dao.FlightQueryBuilder;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.entity.FlightStatus;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ua.nure.lymar.airlines.controller.flightCommands.FlightListUtils.convertTime;
import static ua.nure.lymar.airlines.controller.flightCommands.FlightSearchCommand.emptyDepartureSelection;
import static ua.nure.lymar.airlines.controller.flightCommands.FlightSearchCommand.emptyDestinationSelection;

/**
 * The implementation of FlightDao to db of MySql Type
 *
 * @see FlightDAO
 */
public class MySqlFlightDAO extends MySqlBaseDAO implements FlightDAO {
    private final Logger LOGGER = Logger.getLogger(MySqlFlightDAO.class);

    private Flight getFlightFromDB(ResultSet resultSet) throws DaoException {
        Flight flight;
        try {
            flight = new Flight();
            flight.setId(resultSet.getInt("flight_id"));
            flight.setName(resultSet.getString("flight_name"));
            flight.setDepartureCity(resultSet.getString("flight_departureCity"));
            flight.setDestinationCity(resultSet.getString("flight_destinationCity"));
            flight.setDate(resultSet.getDate("flight_date"));
            flight.setTime(convertTime(resultSet.getTime("flight_time"), true));
            flight.setStatus(FlightStatus.values()[resultSet.getInt("flight_statatus")]);
            flight.setCrew(new Crew());
            flight.getCrew().setId(resultSet.getInt("flight_crew_id"));
            return flight;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Flight read(Integer id) throws DaoException {
        String sql = "SELECT * FROM flight WHERE flight_id =? and isBusyOrDeleted=0;";
        Flight flight = null;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flight = getFlightFromDB(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return flight;
    }

    @Override
    public Integer create(Flight entity) throws DaoException {
        String sql = "INSERT INTO flight " +
                "(flight_name, flight_departureCity, flight_destinationCity, flight_date, flight_time, flight_statatus, flight_crew_id)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = getConnection().
                prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDepartureCity());
            statement.setString(3, entity.getDestinationCity());
            statement.setDate(4, new java.sql.Date(entity.getDate().getTime()));
            statement.setTime(5, entity.getTime());
            statement.setInt(6, entity.getStatus().ordinal());
            if (entity.getCrew() != null) {
                statement.setInt(7, entity.getCrew().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Integer id = null;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Flight entity) throws DaoException {
        String sql;
        if (entity.getCrew().getId() != 0) {
            sql = "UPDATE flight SET flight_name=?, flight_departureCity=?, flight_destinationCity=?," +
                    "flight_date=?, flight_time=?, flight_statatus=?, flight_crew_id=? WHERE flight_id=?;";
        } else {
            sql = "UPDATE flight SET flight_name=?, flight_departureCity=?, flight_destinationCity=?," +
                    "flight_date=?, flight_time=?, flight_statatus=? WHERE flight_id=?;";
        }
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDepartureCity());
            statement.setString(3, entity.getDestinationCity());
            statement.setDate(4, new java.sql.Date(entity.getDate().getTime()));
            entity.setTime(convertTime(entity.getTime(), false));
            statement.setTime(5, entity.getTime());
            statement.setInt(6, entity.getStatus().ordinal());
            if (entity.getCrew().getId() != 0) {
                statement.setInt(7, entity.getCrew().getId());
                statement.setInt(8, entity.getId());
            } else {
                statement.setInt(7, entity.getId());
            }
            LOGGER.debug("sql = " + sql + " parameters" + entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DaoException {
        String sql = "UPDATE flight SET isBusyOrDeleted=1 WHERE flight_id=?;";
        changeToArchive(sql, id);
    }

    @Override
    public List<Flight> readAllFlights() throws DaoException {
        String sql = "SELECT * FROM flight ORDER BY flight_date DESC , flight_time DESC;";
        return getListOfFlight(sql);
    }

    @Override
    public List<Flight> readActualFlights() throws DaoException {
        String sql = "SELECT * FROM flight WHERE isBusyOrDeleted=0 ORDER BY flight_date DESC , flight_time DESC;";
        return getListOfFlight(sql);
    }

    @Override
    public List<Flight> readFlihghtsByNumber(String flightNumber) throws DaoException {
        String sql = "SELECT * FROM flight WHERE flight_name like '%" + flightNumber + "%'";
        return getListOfFlight(sql);
    }


    /*
     * @Override public List<Flight> readFlihghtsByDate(Date flightDate) { // TODO
     * Auto-generated method stub return null; }
     */

    @Override
    public List<Flight> readFlihghtsByDate(Date flightDate) throws DaoException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String dateStr = simpleDateFormat.format(flightDate);
        String sql = "SELECT * FROM epam_airlines.flight WHERE flight_date = '" + dateStr + "'";
        LOGGER.debug(sql);
        return getListOfFlight(sql);

    }

    @Override
    public List<Flight> readFlihghtsByObject(Flight flight) throws DaoException {
        FlightQueryBuilder flightQueryBuilder = new FlightBuilderImpl();
        String sql = "SELECT * FROM epam_airlines.flight WHERE ";
        if (!"".equals(flight.getName())) {
            sql += flightQueryBuilder.addFlightName(flight.getName());
        }
        if (!"".equals(flight.getDepartureCity()) && !emptyDepartureSelection.equals(flight.getDepartureCity())) {
            sql += flightQueryBuilder.addDepartureCity(flight.getDepartureCity());
        }
        if (!"".equals(flight.getDestinationCity()) && !emptyDestinationSelection.equals(flight.getDestinationCity())) {
            sql += flightQueryBuilder.addDestinationCity(flight.getDestinationCity());
        }
        if (flight.getDate() != null) {
            sql += flightQueryBuilder.addFlightDate(flight.getDate());
        }
        sql = sql.replace("WHERE and", "WHERE isBusyOrDeleted=0 and");
        LOGGER.debug(sql);
        return getListOfFlight(sql);
    }


    @Override
    public List<Flight> readFlightsByDepartureAndArrival(String departure, String arrival) throws DaoException {
        String sql = "SELECT * FROM flight WHERE flight_departureCity like '%" + departure + "%' and flight_destinationCity like '%" + arrival + "%'";
        return getListOfFlight(sql);
    }

    @Override
    public List<String> selectAllDestinations() throws DaoException {
        String sql = "SELECT distinct flight_destinationCity FROM flight where isBusyOrDeleted=0 order by flight_destinationCity";
        return selectStringValuesFromFlightTable(sql);
    }

    @Override
    public List<String> selectAllDepartures() throws DaoException {
        String sql = "SELECT distinct flight_departureCity FROM flight where isBusyOrDeleted=0 order by flight_departureCity";
        return selectStringValuesFromFlightTable(sql);
    }

    private List<String> selectStringValuesFromFlightTable(String sql) throws DaoException {
        List<String> result = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return result;
    }

    private List<Flight> getListOfFlight(String sql) throws DaoException {
        Flight flight;
        List<Flight> flights = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                flight = getFlightFromDB(resultSet);
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return flights;
    }

    @Override
    public List<Flight> readNewFlights() throws DaoException {
        String sql = "SELECT * FROM flight WHERE isBusyOrDeleted=0 && flight_statatus=? ORDER BY flight_date DESC , flight_time DESC;";
        Flight flight;
        List<Flight> flights = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, FlightStatus.NEW.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flight = getFlightFromDB(resultSet);
                flights.add(flight);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return flights;
    }
}
