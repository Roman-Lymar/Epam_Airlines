package ua.nure.lymar.airlines.service.logic;

import org.apache.log4j.Logger;
import ua.nure.lymar.airlines.dao.CrewDAO;
import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.StaffDAO;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Staff;
import ua.nure.lymar.airlines.service.CrewService;
import ua.nure.lymar.airlines.service.ServiceException;

import java.util.List;

public class CrewServiceImpl implements CrewService {
    private final Logger LOGGER = Logger.getLogger(CrewServiceImpl.class);
    private CrewDAO crewDAO;
    private StaffDAO staffDAO;

    public void setStaffDAO(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public void setCrewDAO(CrewDAO crewDAO) {
        this.crewDAO = crewDAO;
    }

    @Override
    public Crew readById(Integer id) throws ServiceException {
        try {
            return crewDAO.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Crew> readCrews() throws ServiceException {
        try {
            return crewDAO.getCrews();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Crew> readActualCrews() throws ServiceException {
        try {
            return crewDAO.getActualCrews();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Crew crew) throws ServiceException {
        LOGGER.debug("save crew");
        try{
            if(crew.getId()!=null){
                crewDAO.update(crew);
            } else{
                Integer id =crewDAO.create(crew);
                crew.setId(id);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            crewDAO.delete(id);
            crewDAO.deleteStaffFromDeletedCrew(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Staff> readFreeStaff() throws ServiceException {
        try{
            return staffDAO.getFreeStaffs();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Staff> readStaffFromCrew(Integer id) throws ServiceException {
        try{
            return staffDAO.getStaffsFromCrew(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStaffInCrew(Integer crewId, Integer staffId) throws ServiceException {
        try{
            crewDAO.addStaffinCrew(crewId,staffId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteStaffFromCrew(Integer crewId, Integer staffId) throws ServiceException {
        try {
            crewDAO.deleteStaffFromCrew(crewId,staffId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
