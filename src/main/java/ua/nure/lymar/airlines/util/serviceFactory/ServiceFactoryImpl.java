package ua.nure.lymar.airlines.util.serviceFactory;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.CrewDAO;
import ua.nure.lymar.airlines.dao.FlightDAO;
import ua.nure.lymar.airlines.dao.MessageDAO;
import ua.nure.lymar.airlines.dao.SeverityDAO;
import ua.nure.lymar.airlines.dao.StaffDAO;
import ua.nure.lymar.airlines.dao.UserDAO;
import ua.nure.lymar.airlines.dao.mysql.MySqlCrewDAO;
import ua.nure.lymar.airlines.dao.mysql.MySqlFlightDAO;
import ua.nure.lymar.airlines.dao.mysql.MySqlMessageDAO;
import ua.nure.lymar.airlines.dao.mysql.MySqlSeverityDAO;
import ua.nure.lymar.airlines.dao.mysql.MySqlStaffDAO;
import ua.nure.lymar.airlines.dao.mysql.MySqlUserDAO;
import ua.nure.lymar.airlines.entity.Flight;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.MessageService;
import ua.nure.lymar.airlines.service.SeverityService;
import ua.nure.lymar.airlines.service.StaffService;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.service.logic.CrewServiceImpl;
import ua.nure.lymar.airlines.service.logic.FlightServiceImpl;
import ua.nure.lymar.airlines.service.logic.MessageServiceImpl;
import ua.nure.lymar.airlines.service.logic.SeverityServiceImpl;
import ua.nure.lymar.airlines.service.logic.StaffServiceImpl;
import ua.nure.lymar.airlines.service.logic.UserServiceImpl;
import ua.nure.lymar.airlines.util.connection.ConnectionPool;

public class ServiceFactoryImpl implements ServiceFactory {
    private final Logger LOGGER = Logger.getLogger(ServiceFactoryImpl.class);
    private Connection connection;
    private UserService userService;
    private CrewService crewService;
    private StaffService staffService;
    private MessageService messageService;
    private FlightService flightService;

    @Override
    public UserService getUserService() throws ServiceFactoryException {
        if(this.userService == null) {
            this.userService = new UserServiceImpl();
            this.userService.setUserDAO(getUserDAO());
        }
        return this.userService;
    }

    @Override
    public StaffService getStaffService() throws ServiceFactoryException {
        if(this.staffService == null) {
            this.staffService = new StaffServiceImpl();
            this.staffService.setStaffDAO(getStaffDAO());
        }
        return this.staffService;
    }


    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public CrewService getCrewService() throws ServiceFactoryException {
        if(this.crewService == null) {
            this.crewService = new CrewServiceImpl();
            this.crewService.setCrewDAO(getCrewDAO());
            this.crewService.setStaffDAO(getStaffDAO());
        }
        return this.crewService;
    }

    @Override
    public FlightService getFlightService() throws ServiceFactoryException {
        if(this.flightService == null) {
            this.flightService = new FlightServiceImpl();
            this.flightService.setFlightDAO(getFlightDAO());
        }
        return this.flightService;
    }

    @Override
    public SeverityService getSeverityService() throws ServiceFactoryException {
        SeverityService severityService = new SeverityServiceImpl();
        severityService.setSeverityDAO(getSeverityDAO());
        return severityService;
    }

    @Override
    public MessageService getMessageService() throws ServiceFactoryException {
        if(this.messageService == null) {
            this.messageService = new MessageServiceImpl();
            this.messageService.setMessageDAO(getMessageDAO());
        }
        return messageService;
    }

    @Override
    public FlightDAO getFlightDAO() throws ServiceFactoryException {
        MySqlFlightDAO flightDAO = new MySqlFlightDAO();
        flightDAO.setConnection(getConnection());
        return flightDAO;
    }

    @Override
    public SeverityDAO getSeverityDAO() throws ServiceFactoryException {
        MySqlSeverityDAO severityDAO = new MySqlSeverityDAO();
        severityDAO.setConnection(getConnection());
        return severityDAO;
    }

    @Override
    public MessageDAO getMessageDAO() throws ServiceFactoryException {
        MySqlMessageDAO mySqlMessageDAO = new MySqlMessageDAO();
        mySqlMessageDAO.setConnection(getConnection());
        return mySqlMessageDAO;
    }

    @Override
    public CrewDAO getCrewDAO() throws ServiceFactoryException {
        MySqlCrewDAO crewDAO = new MySqlCrewDAO();
        crewDAO.setConnection(getConnection());
        return crewDAO;
    }

    @Override
    public UserDAO getUserDAO() throws ServiceFactoryException {
        MySqlUserDAO userDAO = new MySqlUserDAO();
        userDAO.setConnection(getConnection());
        return userDAO;
    }

    @Override
    public StaffDAO getStaffDAO() throws ServiceFactoryException {
        MySqlStaffDAO staffDAO = new MySqlStaffDAO();
        staffDAO.setConnection(getConnection());
        return staffDAO;
    }

    @Override
    public Connection getConnection() throws ServiceFactoryException {
        if (connection == null) {
            try {
                ConnectionPool pool = ConnectionPool.getInstance();
                connection = pool.getConnection();
            } catch (SQLException | ClassNotFoundException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}
