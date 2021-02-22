package ua.nure.lymar.airlines.controller.userCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.entity.Role;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class UserEditCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(UserEditCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.debug("userEdit command start");
        Integer id = null;
        setInsertFlagToSession(false, request);
        try {
            id = Integer.parseInt(request.getParameter("id"));
            LOGGER.debug("id=" + id);
        } catch (NumberFormatException e) {
            LOGGER.error("id is not a number");
        }
        if (id != null) {
            try {
                UserService userService = getServiceFactory().getUserService();
                Users user = userService.readById(id);
                request.setAttribute("user", user);
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        request.setAttribute("userRoles", Role.values());
        LOGGER.debug("userEdit command end with" + Pages.USEREDIT_PAGE);
        return Pages.USEREDIT_PAGE;
    }
}
