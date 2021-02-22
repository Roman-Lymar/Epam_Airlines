package ua.nure.lymar.airlines.controller.flightCommands;

import org.apache.log4j.Logger;
import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactory;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static ua.nure.lymar.airlines.controller.flightCommands.FlightSearchCommand.emptyDepartureSelection;
import static ua.nure.lymar.airlines.controller.flightCommands.FlightSearchCommand.emptyDestinationSelection;

public class FlightListUtils {
    public static final DateFormat utcFormat = new SimpleDateFormat("HH:mm:ss");
    public static final DateFormat sysFormat = new SimpleDateFormat("HH:mm:ss");


    public static String refreshFlightSearchTable(HttpSession session, ServiceFactory serviceFactory, HttpServletRequest request, String defaultReturnPage) throws ServletException {
        if (session.getAttribute("flightSearch") != null) {
            try {
                FlightService service = serviceFactory.getFlightService();
                CrewService crewService = serviceFactory.getCrewService();
                List<Flight> flightSearch = (List<Flight>) session.getAttribute("flightSearch");
                service.refreshFlightList(flightSearch);
                Crew crew;
                Integer idc;
                for (Flight f : flightSearch) {
                    crew = f.getCrew();
                    idc = crew.getId();
                    crew = crewService.readById(idc);
                    f.setCrew(crew);
                }
                session.setAttribute("flightSearch", flightSearch);
                setDestAndDepartComboBoxes(request, service);
            } catch (ServiceException | ServiceFactoryException e) {
                throw new ServletException(e);
            }
            return Pages.FLIGHTSEARCH_PAGE;
        }
        return defaultReturnPage;
    }

    public static void setDestAndDepartComboBoxes(HttpServletRequest request, FlightService service) throws ServiceException {
        List<String> departureCityList = service.selectAllDepartureCities();
        departureCityList.add(0, emptyDepartureSelection);
        List<String> destinationCityList = service.selectAllDestinationCities();
        destinationCityList.add(0, emptyDestinationSelection);
        request.setAttribute("destinationCityList", destinationCityList);
        request.setAttribute("departureCityList", departureCityList);
    }

    public static java.sql.Time convertTime(java.sql.Time time, boolean isFromUtcToSys) throws DaoException {
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        sysFormat.setTimeZone(TimeZone.getDefault());
        Date date = null;
        try {
            if (isFromUtcToSys) {
                date = utcFormat.parse(time.toString());
                return Time.valueOf(sysFormat.format(date));
            } else {
                date = sysFormat.parse(time.toString());
                return Time.valueOf(utcFormat.format(date));
            }
        } catch (ParseException e) {
            throw new DaoException(e);
        }
    }
}

