package ua.nure.lymar.airlines.controller.staffCommands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Staff;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.StaffService;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class StaffListCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(StaffListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            StaffService staffService = getServiceFactory().getStaffService();
            List<Staff> staffList = staffService.readActualStaffs();
            PagedListHolderImpl<Staff> listHolder = new PagedListHolderImpl<>(staffList);
            listHolder.setAttribut("staffList");
            listHolder.setPadding(request);
            return Pages.STAFFLIST_PAGE;
        } catch (ServiceFactoryException | ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new ServletException(e);
        }
    }
}
