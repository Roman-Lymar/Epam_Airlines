package ua.nure.lymar.airlines.controller.userCommands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class UserListCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(UserListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try{
            UserService userService = getServiceFactory().getUserService();
            List<Users> userList = userService.readActualUsers();
            PagedListHolderImpl<Users> listHolder = new PagedListHolderImpl<>(userList);
            listHolder.setAttribut("userList");
            listHolder.setPadding(request);
            return Pages.USERLIST_PAGE;
        } catch (ServiceException | ServiceFactoryException e) {
            LOGGER.error(e.getMessage());
            throw new ServletException(e);
        }
    }
}
