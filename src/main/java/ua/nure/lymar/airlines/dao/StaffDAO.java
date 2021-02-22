package ua.nure.lymar.airlines.dao;

import java.util.List;

import ua.nure.lymar.airlines.entity.Staff;

/**
 * Provides an abstract interface to all type of DB for Staff Entity
 *
 * @see DaoException
 * @see DAO
 */
public interface StaffDAO extends DAO<Staff> {

    /**
     * Provides access to staff by it surname that stored in db
     *
     * @return staff entity
     * @throws DaoException
     */
    Staff readBySurname(String surname) throws DaoException;

    /**
     * Provides access to the list of all staffs stored in db
     *
     * @return List of all staffs stored in db
     * @throws DaoException
     */
    List<Staff> getStaffs() throws DaoException;

    /**
     * Provides access to the list of staffs in crew stored in db
     *
     * @return list of staffs in crew
     * @throws DaoException
     */
    List<Staff> getStaffsFromCrew(Integer crew_id) throws DaoException;

    /**
     * Provides access to the list of staffs that not in any crew stored in db
     *
     * @return list of crews, that not in any crew
     * @throws DaoException
     */
    List<Staff> getFreeStaffs() throws DaoException;

    /**
     * Provides access to the list of staffs stored in db , that not in archive
     *
     * @return list of staffs, that not in archive
     * @throws DaoException
     */
    List<Staff> getActualStaffs() throws DaoException;

    /**
     * Delete busy staff from active crew when you delete staff
     *
     * @return crew whithout deleted staff
     * @throws DaoException
     */
	void deleteDeletedStaffFromActiveCrew(Integer id) throws DaoException;
}
