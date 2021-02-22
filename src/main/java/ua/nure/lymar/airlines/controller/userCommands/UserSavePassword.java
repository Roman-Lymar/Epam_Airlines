package ua.nure.lymar.airlines.controller.userCommands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.entity.Role;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.PasswordToHash;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class UserSavePassword extends Command {
    private final Logger LOGGER = Logger.getLogger(UserSavePassword.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.debug("userSavePassword command start");
        Users user = new Users();
        try {
            user.setId(Integer.parseInt(request.getParameter("id")));
        } catch (NumberFormatException e) {
            LOGGER.info("id not number");
            throw new ServletException(e);
        }
        String password = request.getParameter("password");
        if (password != null) {
            password = PasswordToHash.getHashSha256(password);
            user.setPassword(password);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            Users currentUser = (Users) session.getAttribute("currentUser");
            if (currentUser != null && user.getPassword() != null) {
                LOGGER.debug(currentUser);
                try {
                    UserService userService = getServiceFactory().getUserService();
                    userService.savePassword(currentUser.getId(), password);
                    LOGGER.debug("userSavePassword command end");
                    if (currentUser.getUserRole() == Role.ADMIN) return Pages.ADMIN_PAGE;
                    else return Pages.DISPETCHER_PAGE;
                } catch (ServiceFactoryException | ServiceException e) {
                    LOGGER.error(e.getMessage());
                    throw new ServletException(e);
                }
            }
        }
        return Pages.ERROR_PAGE;
    }
}
