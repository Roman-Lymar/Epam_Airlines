package ua.nure.lymar.airlines.controller.staffCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.StaffService;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class StaffDeleteCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(StaffDeleteCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("Id not number");
        }
        if (id != null){
            try {
                StaffService staffService = getServiceFactory().getStaffService();
                staffService.delete(id);
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
            return Commands.STAFFLIST_COMMAND;
    }
}
