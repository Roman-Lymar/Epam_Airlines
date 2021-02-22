package ua.nure.lymar.airlines.service;

import java.util.List;

import ua.nure.lymar.airlines.dao.UserDAO;
import ua.nure.lymar.airlines.entity.Users;

public interface UserService {
    Users readById(Integer id) throws ServiceException;

    Users readByLogin(String login) throws ServiceException;

    List<Users> readUsers() throws ServiceException;

    List<Users> readActualUsers() throws ServiceException;

    void save(Users user) throws ServiceException;

    void delete(Integer id) throws ServiceException;

    void savePassword(Integer id, String pass) throws ServiceException;

    void setUserDAO(UserDAO userDAO);
}
