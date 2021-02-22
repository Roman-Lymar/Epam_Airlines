package ua.nure.lymar.airlines.service;

import java.util.List;

import ua.nure.lymar.airlines.dao.SeverityDAO;
import ua.nure.lymar.airlines.entity.Severity;

public interface SeverityService {

    List<Severity> getAllSeverities() throws ServiceException;

    List<Severity> getEnabledSeverities() throws ServiceException;

    Severity getSeverityById(Integer id) throws ServiceException;

    void setSeverityDAO(SeverityDAO severityDao);
}
