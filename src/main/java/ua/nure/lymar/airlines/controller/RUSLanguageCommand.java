package ua.nure.lymar.airlines.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.util.Pages;

public class RUSLanguageCommand extends Command {
    private final Logger LOGGER = Logger.getLogger(RUSLanguageCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession();
        Config.set(session, Config.FMT_LOCALE, new java.util.Locale("ru", "RU"));
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
        return Pages.INDEX_PAGE;
    }
}
