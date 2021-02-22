package ua.nure.lymar.airlines.controller.adminCommands;

import org.apache.log4j.Logger;
import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.*;
import ua.nure.lymar.airlines.service.MessageService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.SeverityService;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AlertEditCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AlertEditCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            MessageService messageService = getServiceFactory().getMessageService();
            Message message = messageService.getMessageById(Integer.valueOf(request.getParameter("id")));
            Severity severity = getServiceFactory().getSeverityService().getSeverityById(message.getSeverity().getId());
            message.setSeverityDescription(severity.getDescription());
            Users user = getServiceFactory().getUserService().readById(message.getDispatcherId());
            message.setDispatcherName(user.getName() + " " + user.getSurname());
            LOGGER.debug("message = " + message);
            request.setAttribute("message", message);
            request.setAttribute("statuses", Status.values());
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return Pages.ADMIN_ALERT_EDIT_PAGE;
    }
}
