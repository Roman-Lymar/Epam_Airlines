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
import ua.nure.lymar.airlines.util.Commands;
import ua.nure.lymar.airlines.util.PasswordToHash;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class UserSaveCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(UserSaveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Users user = new Users();
        try {
            user.setId(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e) {
            LOGGER.info("id not number");
        }
        user.setLogin(request.getParameter("login"));
        String password = request.getParameter("password");
        if (password != null) {
            LOGGER.debug("password " + password);
            password = PasswordToHash.getHashSha256(password);
            user.setPassword(password);
        }
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setEmail(request.getParameter("email"));
        try {
            user.setUserRole(Role.values()[Integer.parseInt(request.getParameter("userRole"))]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ServletException(e);
        }
        if (user.getLogin() != null && user.getName() != null && user.getSurname() != null
                && user.getEmail() != null && user.getUserRole() != null) {
            try {
                if (!getInsertFlagFromSession(request)) {
                    UserService userService = getServiceFactory().getUserService();
                    userService.save(user);
                    setInsertFlagToSession(true, request);
                }
            } catch (ServiceFactoryException | ServiceException e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }
        return Commands.USERLIST_COMMAND;
    }
}
