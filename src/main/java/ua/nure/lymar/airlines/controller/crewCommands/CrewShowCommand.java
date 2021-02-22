package ua.nure.lymar.airlines.controller.crewCommands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Staff;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class CrewShowCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(CrewShowCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ServletException(e);
        }
        try{
            CrewService crewService = getServiceFactory().getCrewService();
            Crew crew = crewService.readById(id);
            List<Staff> staffInCrew = crewService.readStaffFromCrew(id);
            List<Staff> freeStaff = crewService.readFreeStaff();
            request.setAttribute("crew", crew);
            request.setAttribute("staffInCrew", staffInCrew);
            request.setAttribute("freeStaff", freeStaff);
            return Pages.CREWSHOW_PAGE;
        } catch (ServiceFactoryException | ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new ServletException(e);
        }
    }
}
