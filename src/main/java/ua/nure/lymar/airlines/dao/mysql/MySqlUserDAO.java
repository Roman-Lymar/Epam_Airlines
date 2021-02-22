package ua.nure.lymar.airlines.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.UserDAO;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.entity.Role;

/**
 * The implementation of StaffDAO to db of MySql Type
 *
 * @see UserDAO
 */
public class MySqlUserDAO extends MySqlBaseDAO implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(MySqlUserDAO.class);

    private Users getUserFromDB(ResultSet resultSet) throws DaoException {
        Users user;
        try {
            user = new Users();
            user.setId(resultSet.getInt("user_id"));
            user.setLogin(resultSet.getString("user_login"));
            user.setPassword(resultSet.getString("user_password"));
            user.setName(resultSet.getString("user_name"));
            user.setSurname(resultSet.getString("user_surname"));
            user.setEmail(resultSet.getString("user_email"));
            user.setUserRole(Role.values()[resultSet.getInt("user_role")]);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public Users read(Integer id) throws DaoException {
        String sql = "SELECT * FROM user WHERE user_id =?;";
        Users user = null;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromDB(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public Users readByLogin(String login) throws DaoException {
        String sql = "SELECT * FROM user WHERE isBusyOrDeleted=0 && user_login =?;";
        Users user = null;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromDB(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public List<Users> getUsers() throws DaoException {
        String sql = "SELECT * FROM user ORDER BY user_login;";
        return getListOfUsers(sql);
    }

    @Override
    public List<Users> getActualUsers() throws DaoException {
        String sql = "SELECT * FROM user WHERE isBusyOrDeleted=0 ORDER BY user_login;";
        return getListOfUsers(sql);
    }

    private List<Users> getListOfUsers(String sql) throws DaoException {
        List<Users> users = new ArrayList<>();
        Users user;
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                user = getUserFromDB(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public Integer create(Users user) throws DaoException {
        String sql = "INSERT INTO user(user_login," +
                " user_password, user_name, user_surname, user_email, user_role) " +
                "VALUES (?,?,?,?,?,?);";
        try (PreparedStatement statement =
                     getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getUserRole().ordinal());
            statement.executeUpdate();
            Integer id = null;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DaoException {
        String sql = "UPDATE user SET isBusyOrDeleted=1 WHERE user_id=?;";
        changeToArchive(sql,id);
    }

    @Override
    public void update(Users entity) throws DaoException {
        String sql = "UPDATE user SET user_login=?, user_name=?, " +
                "user_surname=?, user_email=?, user_role=? WHERE user_id=?;";
        try (PreparedStatement statement =
                     getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getEmail());
            statement.setInt(5, entity.getUserRole().ordinal());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void changePassword(Integer id, String pass) throws DaoException {
        String sql = "UPDATE user SET user_password=? WHERE user_id=?;";
        try (PreparedStatement statement =
                     getConnection().prepareStatement(sql)) {
            statement.setString(1, pass);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }
}
