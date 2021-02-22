package ua.nure.lymar.airlines.util.serviceFactory;

import java.sql.Connection;

import ua.nure.lymar.airlines.dao.CrewDAO;
import ua.nure.lymar.airlines.dao.FlightDAO;
import ua.nure.lymar.airlines.dao.MessageDAO;
import ua.nure.lymar.airlines.dao.SeverityDAO;
import ua.nure.lymar.airlines.dao.StaffDAO;
import ua.nure.lymar.airlines.dao.UserDAO;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.FlightService;
import ua.nure.lymar.airlines.service.MessageService;
import ua.nure.lymar.airlines.service.SeverityService;
import ua.nure.lymar.airlines.service.StaffService;
import ua.nure.lymar.airlines.service.UserService;

public interface ServiceFactory extends AutoCloseable {
    UserService getUserService() throws ServiceFactoryException;

    CrewService getCrewService() throws ServiceFactoryException;

    StaffService getStaffService() throws ServiceFactoryException;

    FlightService getFlightService() throws ServiceFactoryException;

    SeverityService getSeverityService() throws ServiceFactoryException;

    MessageService getMessageService() throws ServiceFactoryException;

    UserDAO getUserDAO() throws ServiceFactoryException;

    CrewDAO getCrewDAO() throws ServiceFactoryException;

    StaffDAO getStaffDAO() throws ServiceFactoryException;

    FlightDAO getFlightDAO() throws ServiceFactoryException;

    SeverityDAO getSeverityDAO() throws ServiceFactoryException;

    MessageDAO getMessageDAO() throws ServiceFactoryException;

    Connection getConnection() throws ServiceFactoryException;
}
