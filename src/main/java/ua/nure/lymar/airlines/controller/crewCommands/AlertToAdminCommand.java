package ua.nure.lymar.airlines.controller.crewCommands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Severity;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.SeverityService;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class AlertToAdminCommand extends Command {

    private final Logger LOGGER = Logger.getLogger(AlertToAdminCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ServletException(e);
        }
        Users user = (Users) session.getAttribute("currentUser");
        request.setAttribute("user", user);
        request.setAttribute("crewid", id);
        try {
            SeverityService severityService = getServiceFactory().getSeverityService();
            List<Severity> severities = severityService.getEnabledSeverities();
            request.setAttribute("severities", severities);
        } catch (ServiceFactoryException | ServiceException e) {
            LOGGER.error(e.getMessage());
        }
        return Pages.ADMIN_ALERT_PAGE;
    }
}
