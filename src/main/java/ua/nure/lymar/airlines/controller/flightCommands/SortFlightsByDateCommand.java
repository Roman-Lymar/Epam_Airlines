package ua.nure.lymar.airlines.controller.flightCommands;

import org.apache.log4j.Logger;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

import java.util.List;

public class SortFlightsByDateCommand extends SortFlightCommand {
    private final Logger LOGGER = Logger.getLogger(SortFlightsByDateCommand.class);

    @Override
    public void sort(List<Flight> flightList){
        FlightService flightService = null;
        try {
            flightService = getServiceFactory().getFlightService();
            flightService.sortByDate(flightList);
        } catch (ServiceFactoryException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
