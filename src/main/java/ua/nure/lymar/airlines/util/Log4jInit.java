package ua.nure.lymar.airlines.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInit implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        PropertyConfigurator.configure(sce.getServletContext().getRealPath("WEB-INF/log4j.properties"));
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
