package ua.nure.lymar.airlines.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactory;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public abstract class Command {
    private static final Logger LOGGER = Logger.getLogger(Command.class);
    public static final String INSERT_FLAG = "insertFlag";
    private ServiceFactory serviceFactory;

    protected ServiceFactory getServiceFactory() {
        LOGGER.debug("Command-GetServiceFactory");
        return serviceFactory;
    }

    public void setInsertFlagToSession(boolean insertFlag, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute(INSERT_FLAG, insertFlag);
    }

    public boolean getInsertFlagFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(INSERT_FLAG) == null) return false;
        Boolean isInsert = (Boolean) session.getAttribute(INSERT_FLAG);
        LOGGER.debug("isInsert = " + isInsert);
        return isInsert;
    }

    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public abstract String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, ServiceFactoryException;
}
