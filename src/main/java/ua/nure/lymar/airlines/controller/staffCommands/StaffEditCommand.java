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
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class StaffEditCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(StaffEditCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.debug("StaffEditCommand start");
        setInsertFlagToSession(false, request);
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("id is not a number");
        }
        if (id != null){
            try {
                StaffService staffService = getServiceFactory().getStaffService();
                Staff staff = staffService.readById(id);
                request.setAttribute("staff", staff);
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        request.setAttribute("professions", Profession.values());
        LOGGER.debug("StaffEditCommand end with" + Pages.STAFFEDIT_PAGE);
        return Pages.STAFFEDIT_PAGE;
    }
}
