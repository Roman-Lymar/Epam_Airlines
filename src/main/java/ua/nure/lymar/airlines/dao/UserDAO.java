package ua.nure.lymar.airlines.dao;

import java.util.List;

import ua.nure.lymar.airlines.entity.Users;

/**
 * Provides an abstract interface to all type of DB for Users Entity
 *
 * @see DaoException
 * @see DAO
 */
public interface UserDAO extends DAO<Users> {
    /**
     * Provides access to user by it id that stored in db
     *
     * @return user entity
     * @throws DaoException
     */
    Users readByLogin(String login) throws DaoException;

    /**
     * Provides access to all users stored in db
     *
     * @return list of users
     * @throws DaoException
     */
    List<Users> getUsers() throws DaoException;

    /**
     * Provides access to the list of users stored in db , that not in archive
     *
     * @return list of users, that not in archive
     * @throws DaoException
     */
    List<Users> getActualUsers() throws DaoException;

    /**
     * provides the ability to change the user's password in db
     *
     * @param id of user
     * @param pass new password
     * @throws DaoException
     */
    void changePassword(Integer id, String pass) throws DaoException;
}
