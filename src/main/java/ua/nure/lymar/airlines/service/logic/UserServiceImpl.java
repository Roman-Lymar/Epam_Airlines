package ua.nure.lymar.airlines.service.logic;

import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.UserDAO;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Users readById(Integer id) throws ServiceException {
        try {
            return userDAO.read(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public Users readByLogin(String login) throws ServiceException {
        try {
            return userDAO.readByLogin(login);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Users> readUsers() throws ServiceException {
        try {
            return userDAO.getUsers();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Users> readActualUsers() throws ServiceException {
        try {
            return userDAO.getActualUsers();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Users user) throws ServiceException {
        try {
            if (user.getId()==null) {
                Integer id = userDAO.create(user);
                user.setId(id);
            } else {
                userDAO.update(user);
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            userDAO.delete(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }

    }

    @Override
    public void savePassword(Integer id, String pass) throws ServiceException {
        try {
            userDAO.changePassword(id, pass);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }
}
