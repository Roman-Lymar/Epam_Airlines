package ua.nure.lymar.airlines.service.logic;

import java.util.List;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.SeverityDAO;
import ua.nure.lymar.airlines.entity.Severity;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.SeverityService;

public class SeverityServiceImpl implements SeverityService {
    private SeverityDAO severityDAO;


    @Override
    public List<Severity> getAllSeverities() throws ServiceException {
        try {
            return severityDAO.getAllSeverities();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Severity> getEnabledSeverities() throws ServiceException {
        try {
            return severityDAO.getEnabledSeverities();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Severity getSeverityById(Integer id) throws ServiceException {
        try {
            return severityDAO.getSeverityById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public SeverityDAO getSeverityDAO() {
        return severityDAO;
    }

    @Override
    public void setSeverityDAO(SeverityDAO severityDAO) {
        this.severityDAO = severityDAO;
    }

}
