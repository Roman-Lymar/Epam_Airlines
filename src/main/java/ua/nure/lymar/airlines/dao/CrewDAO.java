package ua.nure.lymar.airlines.dao;

import java.util.List;

import ua.nure.lymar.airlines.entity.Crew;

/**
 * Provides an abstract interface to all type of DB for Crew Entity
 *
 * @see DaoException
 * @see DAO
 */
public interface CrewDAO extends DAO<Crew> {
    /**
     * Provides access to the list of all crews stored in db
     *
     * @return List of all crews stored in db
     * @throws DaoException
     */
    List<Crew> getCrews() throws DaoException;

    /**
     * Provides the ability to add staff in crew
     *
     * @param crewId  id of the crew to which the staff is added
     * @param staffId id of the staff who is added to the crew
     * @throws DaoException
     */
    void addStaffinCrew(Integer crewId, Integer staffId) throws DaoException;

    /**
     * Provides the ability to delete staff from crew
     *
     * @param crewId  id of the crew from which the staff is deleted
     * @param staffId staffId id of the staff who is deleted from the crew
     * @throws DaoException
     */
    void deleteStaffFromCrew(Integer crewId, Integer staffId) throws DaoException;

    /**
     * Provides access to list of crews, that not in archive
     *
     * @return list of crews, that not in archive
     * @throws DaoException
     */
    List<Crew> getActualCrews() throws DaoException;

    /**
     * Provides ability to delete staff from crew_staff 
     *
     * @return make staff not archive
     * @throws DaoException
     */
	void deleteStaffFromDeletedCrew(Integer crewId) throws DaoException;
}
