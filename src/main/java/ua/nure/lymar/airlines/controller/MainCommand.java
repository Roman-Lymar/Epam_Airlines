package ua.nure.lymar.airlines.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.util.Pages;

public class MainCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(MainCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Users user = (Users) session.getAttribute("currentUser");
            if (user != null) {
                LOGGER.debug("current user:" + user.getLogin());
                switch (user.getUserRole()) {
                    case ADMIN:
                        return Pages.ADMIN_PAGE;
                    case DISPETCHER:
                        return Pages.DISPETCHER_PAGE;
                }
            }
        }
        return Pages.LOGIN_PAGE;
    }
}
