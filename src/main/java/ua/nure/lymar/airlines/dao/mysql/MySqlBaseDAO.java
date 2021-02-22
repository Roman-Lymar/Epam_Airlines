package ua.nure.lymar.airlines.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DAO;
import ua.nure.lymar.airlines.dao.DaoException;

/**
 * The implementation of BaseDao to db of MySql Type
 *
 * @see DAO
 */
public class MySqlBaseDAO {
    private static final Logger LOGGER = Logger.getLogger(MySqlBaseDAO.class);
    private Connection connection;

    Connection getConnection() {
        LOGGER.debug("connection =  " + connection);
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    void changeToArchive(String sql, Integer id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }
}
