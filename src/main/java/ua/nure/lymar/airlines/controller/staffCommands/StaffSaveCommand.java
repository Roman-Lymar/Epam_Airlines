package ua.nure.lymar.airlines.controller.staffCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Profession;
import ua.nure.lymar.airlines.entity.Staff;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.StaffService;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class StaffSaveCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(StaffSaveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Staff staff = new Staff();
        try {
            staff.setId(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e) {
            LOGGER.error("id not number");
        }
        staff.setName(request.getParameter("name"));
        staff.setSurname(request.getParameter("surname"));
        try {
            staff.setProfession(Profession.values()[Integer.parseInt(request.getParameter("profession"))]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            LOGGER.error(e.getMessage());
            throw new ServletException(e);
        }
        LOGGER.debug("is insert flag = " + getInsertFlagFromSession(request));
        if (staff.getSurname() != null && staff.getName() != null
                && staff.getProfession() != null && !getInsertFlagFromSession(request)) {
            try {
                StaffService staffService = getServiceFactory().getStaffService();
                staffService.save(staff);
                setInsertFlagToSession(true, request);
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        return Commands.STAFFLIST_COMMAND;
    }
}
