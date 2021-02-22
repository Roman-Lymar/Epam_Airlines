package ua.nure.lymar.airlines.dao;

import java.util.List;

import ua.nure.lymar.airlines.entity.Severity;

public interface SeverityDAO {

    List<Severity> getAllSeverities() throws DaoException;

    List<Severity> getEnabledSeverities() throws DaoException;

    Severity getSeverityById(Integer id) throws DaoException;

}
