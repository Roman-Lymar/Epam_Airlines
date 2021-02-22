package ua.nure.lymar.airlines.service.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.FlightDAO;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.ServiceException;

public class FlightServiceImpl implements FlightService {
    private FlightDAO flightDAO;
    private final Logger LOGGER = Logger.getLogger(FlightServiceImpl.class);

    public void setFlightDAO(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public Flight readById(Integer id) throws ServiceException {
        try {
            return flightDAO.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Flight> readFlights() throws ServiceException {
        try {
            return flightDAO.readAllFlights();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Flight> readActualFlights() throws ServiceException {
        try {
            return flightDAO.readActualFlights();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Flight> readNewFlights() throws ServiceException {
        try {
            return flightDAO.readNewFlights();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Flight flight) throws ServiceException {
        try {
            if (flight.getId() != null) {
                flightDAO.update(flight);
            } else {
                Integer id = flightDAO.create(flight);
                flight.setId(id);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            flightDAO.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void sortByName(List<Flight> flights) {
        flights.sort(new Comparator<Flight>() {
            @Override
            public int compare(Flight flight, Flight t1) {
                return flight.getName().compareTo(t1.getName());
            }
        });
    }

    @Override
    public void sortByDepartureCity(List<Flight> flights) {
        flights.sort(new Comparator<Flight>() {
            @Override
            public int compare(Flight flight, Flight t1) {
                return flight.getDepartureCity().compareTo(t1.getDepartureCity());
            }
        });
    }

    @Override
    public void sortByArrivalCity(List<Flight> flights) {
        flights.sort(new Comparator<Flight>() {
            @Override
            public int compare(Flight flight, Flight t1) {
                return flight.getDestinationCity().compareTo(t1.getDestinationCity());
            }
        });
    }

    @Override
    public void sortByDate(List<Flight> flights) {
    	flights.sort(new Comparator<Flight>() {
    		@Override
    		public int compare(Flight flight, Flight t1) {
    			return flight.getDate().compareTo(t1.getDate());
    		}
    	});
    }

    @Override
    public List<Flight> readFlightsByNumber(String flightNumber) throws ServiceException {
        try {
            return flightDAO.readFlihghtsByNumber(flightNumber);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Flight> readFlightsByDate(Date flightDate) throws ServiceException {
        LOGGER.debug(flightDate);
        try {
            return flightDAO.readFlihghtsByDate(flightDate);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> selectAllDepartureCities() throws ServiceException {
        try {
            return flightDAO.selectAllDepartures();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<String> selectAllDestinationCities() throws ServiceException {
        try {
            return flightDAO.selectAllDestinations();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Flight> searchByFlight(Flight flight) throws ServiceException {
        try {
            return flightDAO.readFlightsByDepartureAndArrival(flight.getDepartureCity(), flight.getDestinationCity());
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Flight> readFlightsByMultipleParameters(Flight flight) throws ServiceException {
        try {
            return flightDAO.readFlihghtsByObject(flight);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void refreshFlightList(List<Flight> flights) throws ServiceException {
        List<Flight> flightsToDelete = new ArrayList<>();
        for (Flight flight : flights) {
            Flight freshFlight = readById(flight.getId());
            LOGGER.debug("fresh flight = " + freshFlight);
            if (freshFlight == null)
                flightsToDelete.add(flight);
            else
                flights.set(flights.indexOf(flight), freshFlight);
        }
        for (Flight flight : flightsToDelete) {
            flights.remove(flight);
        }
    }

}
