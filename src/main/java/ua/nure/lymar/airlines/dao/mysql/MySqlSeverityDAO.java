package ua.nure.lymar.airlines.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.SeverityDAO;
import ua.nure.lymar.airlines.entity.Severity;

public class MySqlSeverityDAO implements SeverityDAO {

    private static final Logger LOGGER = Logger.getLogger(MySqlBaseDAO.class);
    private Connection connection;

    Connection getConnection() {
        LOGGER.debug("connection =  " + connection);
        return connection;
    }

    @Override
    public List<Severity> getAllSeverities() throws DaoException {
        String sql = "SELECT * FROM severity ORDER BY severity_id ASC;";
        return getListOfSeverity(sql);
    }

    @Override
    public List<Severity> getEnabledSeverities() throws DaoException {
        String sql = "SELECT * FROM severity WHERE isEnable = 1 ORDER BY severity_id ASC;";
        return getListOfSeverity(sql);
    }

    @Override
    public Severity getSeverityById(Integer id) throws DaoException {
        String sql = "SELECT * FROM severity WHERE severity_id = " + id + " ORDER BY severity_id ASC;";
        return getListOfSeverity(sql).get(0);
    }


    private List<Severity> getListOfSeverity(String sql) throws DaoException {
        List<Severity> severities = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Severity severity = new Severity();
                severity.setId(resultSet.getInt("severity_id"));
                severity.setDescription(resultSet.getString("description"));
                severities.add(severity);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return severities;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
