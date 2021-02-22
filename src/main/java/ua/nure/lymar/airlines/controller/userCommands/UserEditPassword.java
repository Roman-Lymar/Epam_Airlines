package ua.nure.lymar.airlines.controller.userCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class UserEditPassword extends Command {
    private final Logger LOGGER = Logger.getLogger(UserEditPassword.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.debug("userEditPassword command start");
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error("id is not a number, throw exception");
            throw new ServletException(e);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            Users currentUser = (Users) session.getAttribute("currentUser");
            LOGGER.error("userEditPassword currnetUser " + currentUser.getLogin());
            if (currentUser != null) {
                try {
                    UserService userService = getServiceFactory().getUserService();
                    Users user = userService.readById(id);
                    if (user.getUserRole() == currentUser.getUserRole()) {
                        request.setAttribute("user", user);
                        LOGGER.debug("userEditPassword command end with" + Pages.USEREDITPASS_PAGE);
                        return Pages.USEREDITPASS_PAGE;
                    }
                } catch (ServiceFactoryException | ServiceException e) {
                    LOGGER.error(e.getMessage());
                    throw new ServletException(e);
                }
            }
        }
        return Pages.ERROR_PAGE;
    }

}
