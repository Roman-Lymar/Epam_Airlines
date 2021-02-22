package ua.nure.lymar.airlines.service.logic;

import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.StaffDAO;
import ua.nure.lymar.airlines.entity.Staff;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.StaffService;

public class StaffServiceImpl implements StaffService {
    private static final Logger LOGGER = Logger.getLogger(StaffServiceImpl.class);
    private StaffDAO staffDAO;

    public void setStaffDAO(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @Override
    public Staff readById(Integer id) throws ServiceException {
        try {
            return staffDAO.read(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public Staff readByLastname(String surname) throws ServiceException {
        try {
            return staffDAO.readBySurname(surname);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Staff> readStaffs() throws ServiceException {
        try {
            return staffDAO.getStaffs();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Staff> readActualStaffs() throws ServiceException {
        try {
            return staffDAO.getActualStaffs();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Staff> readStaffFromCrew(Integer id) throws ServiceException {
        try {
            return staffDAO.getStaffsFromCrew(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Staff staff) throws ServiceException {
        try {
            if (staff.getId() == null) {
                Integer id = staffDAO.create(staff);
                staff.setId(id);
            } else {
                staffDAO.update(staff);
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            staffDAO.delete(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }
}
