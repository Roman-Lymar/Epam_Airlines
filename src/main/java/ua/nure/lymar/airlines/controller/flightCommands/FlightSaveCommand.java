package ua.nure.lymar.airlines.controller.flightCommands;

import org.apache.log4j.Logger;
import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.*;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;

public class FlightSaveCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(FlightSaveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        Flight flight = new Flight();
        if (id != null) {
            try {
                FlightService flightService = getServiceFactory().getFlightService();
                flight = flightService.readById(id);
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        } else {
            flight.setStatus(FlightStatus.NEW);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            Users user = (Users) session.getAttribute("currentUser");
            if (user != null) {
                if (user.getUserRole() == Role.ADMIN) {
                    flight.setName(request.getParameter("name"));
                    flight.setDepartureCity(request.getParameter("departureCity"));
                    flight.setDestinationCity(request.getParameter("destinationCity"));
                    flight.setDate(Date.valueOf(request.getParameter("date")));
                    flight.setTime(Time.valueOf(request.getParameter("time") + ":00"));
                } else {
                    try {
                        flight.setStatus(FlightStatus.values()[Integer.parseInt(request.getParameter("flightStatus"))]);
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        throw new ServletException(e);
                    }
                    Integer crewId = null;
                    try {
                        crewId = Integer.parseInt(request.getParameter("crewId"));
                    } catch (NumberFormatException e) {
                    }
                    if (crewId != null) {
                        try {
                            CrewService service = getServiceFactory().getCrewService();
                            Crew crew = service.readById(crewId);
                            flight.setCrew(crew);
                        } catch (ServiceException | ServiceFactoryException e) {
                            LOGGER.error(e.getMessage());
                            throw new ServletException(e);
                        }
                    }
                }
            }
        }
        if (flight.getName() != null && flight.getDepartureCity() != null
                && flight.getDestinationCity() != null && flight.getDate() != null
                && flight.getTime() != null && flight.getStatus() != null) {
            try {
                if (!getInsertFlagFromSession(request)) {
                    FlightService flightService = getServiceFactory().getFlightService();
                    flightService.save(flight);
                    setInsertFlagToSession(true, request);
                }
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        return FlightListUtils.refreshFlightSearchTable(session, getServiceFactory(), request, Commands.FLIGHTLIST_COMMAND);
    }
}
