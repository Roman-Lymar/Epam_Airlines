package ua.nure.lymar.airlines.controller.adminCommands;

import org.apache.log4j.Logger;
import ua.nure.lymar.airlines.entity.Message;
import ua.nure.lymar.airlines.entity.Status;
import ua.nure.lymar.airlines.service.MessageService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AlertSaveCommand extends ua.nure.lymar.airlines.controller.Command {
    private static final Logger LOGGER = Logger.getLogger(AlertSaveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceFactoryException {
        Status status = Status.values()[Integer.parseInt(request.getParameter("status"))];
        LOGGER.debug("staus is = " + status.getStatus());
        try {
            MessageService messageService = getServiceFactory().getMessageService();
            Message message = new Message();
            LOGGER.debug("message id = " + request.getParameter("id"));
            message.setId(Integer.parseInt(request.getParameter("id")));
            message.setStatus(status);
            messageService.updateMessageStatus(message);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServiceFactoryException(e);
        }
        return Commands.ALERT_LIST_COMMAND;
    }
}
