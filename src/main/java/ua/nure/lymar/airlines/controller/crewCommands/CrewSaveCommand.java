package ua.nure.lymar.airlines.controller.crewCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class CrewSaveCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(CrewSaveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Crew crew = new Crew();
        try {
            crew.setId(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e) {
        }
        crew.setName(request.getParameter("name"));
        HttpSession session = request.getSession(false);
        if (session != null) {
            Users user = (Users) session.getAttribute("currentUser");
            crew.setUser(user);
        } else
            return Pages.ERROR_PAGE;
        if (crew.getName() == null && crew.getUser() == null)
            return Pages.ERROR_PAGE;
        else {
            if (!getInsertFlagFromSession(request)) {
                try {
                    CrewService crewService = getServiceFactory().getCrewService();
                    crewService.save(crew);
                } catch (ServiceFactoryException | ServiceException e) {
                    LOGGER.error(e.getMessage());
                    throw new ServletException(e);
                }
                setInsertFlagToSession(true, request);
            }
        }
        return Commands.CREWLIST_COMMAND;
    }
}
