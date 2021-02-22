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
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class CrewEditCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(CrewEditCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.debug("CrewEditCommand starts");
        Integer id = null;
        setInsertFlagToSession(false, request);
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        if (id != null) {
            try {
                CrewService service = getServiceFactory().getCrewService();
                Crew crew = service.readById(id);
                request.setAttribute("crew", crew);
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            Users user = (Users) session.getAttribute("currentUser");
            request.setAttribute("user", user);
        } else
            return Pages.ERROR_PAGE;
        return Pages.CREWEDIT_PAGE;
    }
}
