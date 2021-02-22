package ua.nure.lymar.airlines.controller.crewCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class CrewDeleteCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(CrewDeleteCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("ID not number");
        }
        if (id != null){
            try {
                CrewService crewService = getServiceFactory().getCrewService();
                crewService.delete(id);
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        return Commands.CREWLIST_COMMAND;
    }
}
