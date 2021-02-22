package ua.nure.lymar.airlines.controller.flightCommands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

import static ua.nure.lymar.airlines.controller.flightCommands.FlightListUtils.setDestAndDepartComboBoxes;

public class FlightSearchCommand extends Command {
    public static final String emptyDestinationSelection = "any destination";
    public static final String emptyDepartureSelection = "any departure";
    private final Logger LOGGER = Logger.getLogger(FlightSearchCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        List<Flight> flightSearch = (List<Flight>) session.getAttribute("flightSearch");
        try {
            FlightService service = getServiceFactory().getFlightService();
            if (flightSearch != null) {
                service.refreshFlightList(flightSearch);
                setPageHolder(flightSearch, request);
            }
            setDestAndDepartComboBoxes(request, service);
        } catch (ServiceFactoryException | ServiceException e) {
            LOGGER.error(e.getMessage());
        }
        return Pages.FLIGHTSEARCH_PAGE;
    }

    private void setPageHolder(List<Flight> flights, HttpServletRequest request) {
        PagedListHolderImpl<Flight> listHolder = new PagedListHolderImpl<>(flights);
        listHolder.setAttribut("flightList");
        listHolder.setPadding(request);
    }
}
