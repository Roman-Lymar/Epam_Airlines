package ua.nure.lymar.airlines.controller.flightCommands;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static ua.nure.lymar.airlines.controller.flightCommands.SortFlightsByNameCommand.FLIGHT_LIST;

public abstract class SortFlightCommand extends Command {
    public static final String FLIGHT_LIST = "flightList";

    public abstract void sort(List<Flight> flightList);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        List<Flight> flightList = (List<Flight>) session.getAttribute(FLIGHT_LIST);
        sort(flightList);
        session.setAttribute(FLIGHT_LIST, flightList);
        PagedListHolderImpl<Flight> listHolder = new PagedListHolderImpl<>(flightList);
        listHolder.setAttribut("flightList");
        listHolder.setPadding(request);
        return Pages.FLIGHTLIST_PAGE;
    }
}
