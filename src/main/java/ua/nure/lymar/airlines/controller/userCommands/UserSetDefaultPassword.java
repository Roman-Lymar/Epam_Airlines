package ua.nure.lymar.airlines.controller.userCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.PasswordToHash;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class UserSetDefaultPassword extends Command {
    private final Logger LOGGER = Logger.getLogger(UserSetDefaultPassword.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.debug("UserSetDefaultPassword command start");
        Users user = new Users();
        try {
            user.setId(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e) {
            LOGGER.info("id not number");
        }
        //Default Password
        String password = "111111";
        password = PasswordToHash.getHashSha256(password);
        user.setPassword(password);
        if (user != null) {
            try {
                UserService userService = getServiceFactory().getUserService();
                userService.savePassword(user.getId(), password);
                LOGGER.debug("UserSetDefaultPassword command end");
                return Commands.USERLIST_COMMAND;
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        return Pages.ERROR_PAGE;
    }
}
