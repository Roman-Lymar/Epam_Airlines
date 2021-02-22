package ua.nure.lymar.airlines.controller.crewCommands;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.controller.Command;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.PagedListHolderImpl;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class CrewListCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(CrewListCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            LOGGER.debug("in crew list command");
            CrewService crewService = getServiceFactory().getCrewService();
            List<Crew> crewList = crewService.readActualCrews();
            if(!crewList.isEmpty()){
                UserService userService = getServiceFactory().getUserService();
                Integer id;
                Users user;
                for (Crew c:crewList) {
                    user = c.getUser();
                    id = user.getId();
                    user = userService.readById(id);
                    c.setUser(user);
                }

            }
            PagedListHolderImpl<Crew> listHolder = new PagedListHolderImpl<>(crewList);
            listHolder.setAttribut("crewList");
            listHolder.setPadding(request);
            return Pages.CREWLIST_PAGE;
        } catch (ServiceFactoryException | ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new ServletException(e);
        }
    }
}
