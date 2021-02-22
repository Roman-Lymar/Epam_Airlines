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
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.entity.Role;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class FlightListCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(FlightListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        List<Flight> flightList = new ArrayList<>();
        try {
            Users currentUser = (Users) session.getAttribute("currentUser");
            FlightService service = getServiceFactory().getFlightService();
            if (currentUser.getUserRole() == Role.ADMIN) {
                flightList = service.readActualFlights();
            } else if (currentUser.getUserRole() == Role.DISPETCHER) {
                flightList = service.readNewFlights();
            }
            if (!flightList.isEmpty()) {
                CrewService crewService = getServiceFactory().getCrewService();
                Crew crew;
                Integer id;
                for (Flight f : flightList) {
                    crew = f.getCrew();
                    id = crew.getId();
                    crew = crewService.readById(id);
                    f.setCrew(crew);
                }
            }
            session.setAttribute("flightList", flightList);
        } catch (ServiceFactoryException | ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new ServletException(e);
        }
        setPageHolder(flightList, request);
        return Pages.FLIGHTLIST_PAGE;
    }

    private void setPageHolder(List<Flight> flights, HttpServletRequest request) {
        PagedListHolderImpl<Flight> listHolder = new PagedListHolderImpl<>(flights);
        listHolder.setAttribut("flightList");
        listHolder.setPadding(request);
    }
}
