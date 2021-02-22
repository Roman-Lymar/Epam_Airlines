package ua.nure.lymar.airlines.controller.flightCommands;

import static ua.nure.lymar.airlines.controller.flightCommands.FlightListUtils.setDestAndDepartComboBoxes;
import static ua.nure.lymar.airlines.controller.flightCommands.FlightSearchCommand.emptyDepartureSelection;
import static ua.nure.lymar.airlines.controller.flightCommands.FlightSearchCommand.emptyDestinationSelection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class FlightSearchResultCommand extends Command {

    private final Logger LOGGER = Logger.getLogger(FlightSearchResultCommand.class);

    public Date dateFormat(String flightDate) throws ParseException {
        SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd");
        Date l_date = to.parse(flightDate);
        return l_date;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            FlightService service = getServiceFactory().getFlightService();
            Flight flight = new Flight();
            flight.setName(request.getParameter("name"));
            flight.setDepartureCity(request.getParameter("departureCityId"));
            flight.setDestinationCity(request.getParameter("destinationCityId"));
            LOGGER.debug("before");
            String dateStr = request.getParameter("date");
            if (!"".equals(dateStr)) {
                flight.setDate(dateFormat(dateStr));
            }
            LOGGER.debug("namer = " + flight.getName());
            LOGGER.debug("departure = " + flight.getDepartureCity());
            LOGGER.debug("destination = " + flight.getDestinationCity());
            LOGGER.debug("date = " + flight.getDate());
            List<Flight> searchResult = new ArrayList<>();
            if (!"".equals(flight.getName()) || !emptyDestinationSelection.equals(flight.getDestinationCity()) || !emptyDepartureSelection.equals(flight.getDepartureCity()) || flight.getDate() != null) {
                searchResult = service.readFlightsByMultipleParameters(flight);
                if (!searchResult.isEmpty()) {
                    CrewService crewService = getServiceFactory().getCrewService();
                    Crew crew;
                    Integer id;
                    for (Flight f : searchResult) {
                        crew = f.getCrew();
                        id = crew.getId();
                        crew = crewService.readById(id);
                        f.setCrew(crew);
                    }
                }
            }

            HttpSession session = request.getSession(false);
            session.setAttribute("flightSearch", searchResult);
            setDestAndDepartComboBoxes(request, service);
        } catch (ServiceFactoryException | ParseException | ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new ServletException(e);
        }
        return Pages.FLIGHTSEARCH_PAGE;
    }

}
