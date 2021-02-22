package ua.nure.lymar.airlines.controller.adminCommands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Message;
import ua.nure.lymar.airlines.entity.Severity;
import ua.nure.lymar.airlines.entity.Status;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.MessageService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.SeverityService;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class AlertListCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AlertListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            MessageService messageService = getServiceFactory().getMessageService();
            SeverityService severityService = getServiceFactory().getSeverityService();
            UserService userService = getServiceFactory().getUserService();
            List<Message> messages = messageService.getAllMessages();
            for (Message message : messages) {
                Severity severity = severityService.getSeverityById(message.getSeverity().getId());
                message.setSeverityDescription(severity.getDescription());
                Users user = userService.readById(message.getDispatcherId());
                message.setDispatcherName(user.getName() + " " + user.getSurname());
                LOGGER.debug("message = " + message);
            }
            PagedListHolderImpl<Message> listHolder = new PagedListHolderImpl<>(messages);
            listHolder.setAttribut("messages");
            listHolder.setPadding(request);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return Pages.MESSAGES_FROM_DISPATCHERS_PAGE;
    }
}
