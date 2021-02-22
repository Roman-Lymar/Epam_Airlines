package ua.nure.lymar.airlines.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.CrewDAO;
import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.entity.Crew;
import ua.nure.lymar.airlines.entity.Users;

/**
 * The implementation of CrewDao to db of MySql Type
 *
 * @see CrewDAO
 */
public class MySqlCrewDAO extends MySqlBaseDAO implements CrewDAO {
    private final Logger LOGGER = Logger.getLogger(MySqlCrewDAO.class);

    @Override
    public List<Crew> getCrews() throws DaoException {
        String sql = "SELECT * FROM crew where isBusyOrDeleted=0 ORDER BY crew_name";
        return getListOfCrew(sql);
    }

    @Override
    public Crew read(Integer id) throws DaoException {
        String sql = "SELECT * FROM crew WHERE crew_id=?";
        Crew crew = null;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                crew = getCrewFromDB(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return crew;
    }

    private Crew getCrewFromDB(ResultSet resultSet) throws DaoException {
        Crew crew;
        try {
            crew = new Crew();
            crew.setId(resultSet.getInt("crew_id"));
            crew.setName(resultSet.getString("crew_name"));
            crew.setUser(new Users());
            crew.getUser().setId(resultSet.getInt("user_id"));
            return crew;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer create(Crew entity) throws DaoException {
        String sql = "INSERT INTO crew (crew_name, user_id) VALUES (?,?);";
        try (PreparedStatement statement = getConnection().
                prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getUser().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Integer id = null;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Crew entity) throws DaoException {
        String sql = "UPDATE crew SET crew_name=?, user_id=? WHERE crew_id=?;";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getUser().getId());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DaoException {
        String sql = "UPDATE crew SET isBusyOrDeleted=1 WHERE crew_id=?;";
        changeToArchive(sql, id);
    }

    @Override
    public void addStaffinCrew(Integer crewId, Integer staffId) throws DaoException {
        String sql = "INSERT INTO crew_staff VALUES (?,?);";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, crewId);
            statement.setInt(2, staffId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteStaffFromDeletedCrew(Integer crewId) throws DaoException {
		String sql = "DELETE FROM crew_staff WHERE crew_id=?";
		try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
			statement.setInt(1, crewId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

    @Override
    public void deleteStaffFromCrew(Integer crewId, Integer staffId) throws DaoException {
        String sql = "DELETE FROM crew_staff WHERE crew_id=? AND staff_id=?;";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, crewId);
            statement.setInt(2, staffId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Crew> getActualCrews() throws DaoException {
        String sql = "SELECT * FROM crew WHERE isBusyOrDeleted=0 ORDER BY crew_name;";
        return getListOfCrew(sql);
    }

    private List<Crew> getListOfCrew(String sql) throws DaoException{
        List<Crew> crewList = new ArrayList<>();
        Crew crew;
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                crew = getCrewFromDB(resultSet);
                crewList.add(crew);
                LOGGER.debug("crew list to add = " + crew.getName());
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return crewList;
    }
}
