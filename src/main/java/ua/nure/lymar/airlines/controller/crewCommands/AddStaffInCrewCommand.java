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

public class AddStaffInCrewCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(AddStaffInCrewCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.debug("CrewId=" + request.getParameter("crewId"));
        LOGGER.debug("StaffId=" + request.getParameter("staffId"));
        Integer crewId;
        Integer staffId;
        try {
            crewId = Integer.parseInt(request.getParameter("crewId"));
            staffId = Integer.parseInt(request.getParameter("staffId"));
        } catch (NumberFormatException e) {
            throw new ServletException(e);
        }
        if (crewId != null && staffId != null) {
            try {
                CrewService service = getServiceFactory().getCrewService();
                service.addStaffInCrew(crewId, staffId);
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }
        return Commands.CREWSHOW_COMMAND + "?id=" + crewId;
    }
}
