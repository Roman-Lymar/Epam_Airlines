package ua.nure.lymar.airlines.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.connection.ConnectionPool;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactory;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryImpl;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Controller.class);
    private ConnectionPool pool;
    private ServiceFactory serviceFactory;

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Controller starts");
        String url = req.getRequestURI();
        String contex = req.getContextPath();
        url = url.substring(contex.length());
        Command command = CommandMap.getCommand(url);
        LOGGER.debug("Controller has command " + url);

        String page = Pages.ERROR_PAGE;
        if (command != null) {
            try (ServiceFactory factory = getServiceFactory()) {
                LOGGER.debug("Controller has ServiceFactory" + factory);
                command.setServiceFactory(factory);
                LOGGER.debug("command=" + command);
                page = command.execute(req, resp);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                throw new ServletException(e);
            }
        }

        LOGGER.debug("controller get RD" + page);
        req.getRequestDispatcher(page).forward(req, resp);
        LOGGER.debug("Controller end with page" + page);
    }

    private ServiceFactory getServiceFactory() {
        if(this.serviceFactory == null)
            this.serviceFactory = new ServiceFactoryImpl();
        return this.serviceFactory;
    }

    @Override
    public void init() throws ServletException {
        LOGGER.debug("Contrroller start init");
        try {
            pool = ConnectionPool.getInstance();
            LOGGER.debug("Contrroller end init");
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
}
