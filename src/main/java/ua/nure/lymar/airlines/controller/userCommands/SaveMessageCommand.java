package ua.nure.lymar.airlines.controller.userCommands;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Message;
import ua.nure.lymar.airlines.entity.Severity;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.MessageService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class SaveMessageCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(SaveMessageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ServletException(e);
        }
        LOGGER.debug("crew id = " + id);
        Message message = new Message();
        HttpSession session = request.getSession(false);
        Users user = (Users) session.getAttribute("currentUser");
        message.setDispatcherId(user.getId());
        message.setIssuedDate(new Date());
        Severity severity = new Severity();
        LOGGER.debug("severity = " + request.getParameter("severity"));
        severity.setId(Integer.valueOf(request.getParameter("severity")));
        message.setSeverity(severity);
        message.setMessageText(request.getParameter("message_text"));
        try {
            MessageService messageService = getServiceFactory().getMessageService();
            Integer messageId = messageService.saveMessage(message);
            message.setId(messageId);
        } catch (ServiceFactoryException | ServiceException e) {
            LOGGER.error(e.getMessage());
        }
        return Commands.CREWSHOW_COMMAND + "?id=" + id;
    }
}
