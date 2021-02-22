package ua.nure.lymar.airlines.service;

import java.util.List;

import ua.nure.lymar.airlines.dao.CrewDAO;
import ua.nure.lymar.airlines.dao.StaffDAO;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Staff;

public interface CrewService {
    Crew readById(Integer id) throws ServiceException;

    List<Crew> readCrews() throws ServiceException;

    List<Crew> readActualCrews() throws ServiceException;

    void save (Crew crew) throws ServiceException;

    void delete(Integer id) throws ServiceException;

    List<Staff> readFreeStaff() throws ServiceException;

    List<Staff> readStaffFromCrew(Integer id) throws ServiceException;

    void addStaffInCrew(Integer crewId, Integer staffId) throws ServiceException;

    void deleteStaffFromCrew(Integer crewId, Integer staffId) throws ServiceException;

    void setCrewDAO(CrewDAO crewDAO);

    void setStaffDAO(StaffDAO staffDAO);
}
