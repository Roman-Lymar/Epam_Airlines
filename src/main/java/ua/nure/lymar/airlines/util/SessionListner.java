package ua.nure.lymar.airlines.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.entity.Users;

public class SessionListner implements HttpSessionListener {
    private final Logger LOGGER = Logger.getLogger(SessionListner.class);
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOGGER.debug("Session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Users user = (Users) httpSessionEvent.getSession().getAttribute("currentUser");
        LOGGER.info("Session with user: " + user.getLogin() + "destroyed");
    }
}
