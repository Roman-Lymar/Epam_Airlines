package ua.nure.lymar.airlines.controller.flightCommands;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.entity.FlightStatus;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.entity.Role;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class FlightEditCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(FlightEditCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Integer id = null;
        setInsertFlagToSession(false, request);
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("id is not a number");
        }
        if (id != null) {
            try {
                FlightService flightService = getServiceFactory().getFlightService();
                Flight flight = flightService.readById(id);
                CrewService crewService = getServiceFactory().getCrewService();
                Crew crew = crewService.readById(flight.getCrew().getId());
                flight.setCrew(crew);
                request.setAttribute("flight", flight);
                if (flight.getCrew() != null) {
                    List<FlightStatus> status = new ArrayList<>();
                    status.add(flight.getStatus());
                    request.setAttribute("flightStatus", status);
                }
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        HttpSession session = request.getSession(false);
        LOGGER.debug("session in flight edit command = " + session);
        if (session != null) {
            Users user = (Users) session.getAttribute("currentUser");
            LOGGER.debug("user in flight edit command = " + user.getName() + " role = " + user.getUserRole());
            if (user != null) {
                if (user.getUserRole() == Role.DISPETCHER) {
                    CrewService crewService = null;
                    try {
                        crewService = getServiceFactory().getCrewService();
                        List<Crew> crews = crewService.readCrews();
                        for (Crew crew : crews) {
                            LOGGER.debug(crew.getName());
                        }
                        request.setAttribute("crewList", crews);
                    } catch (ServiceFactoryException | ServiceException e) {
                        LOGGER.error(e.getMessage());
                        throw new ServletException(e);
                    }
                    request.setAttribute("flightStatus", FlightStatus.values());
                }
            }
        }
        return Pages.FLIGHTEDIT_PAGE;
    }
}
