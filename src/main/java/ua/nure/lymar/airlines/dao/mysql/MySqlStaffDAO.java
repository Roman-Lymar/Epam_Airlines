package ua.nure.lymar.airlines.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.StaffDAO;
import ua.nure.lymar.airlines.entity.Profession;
import ua.nure.lymar.airlines.entity.Staff;

/**
 * The implementation of StaffDAO to db of MySql Type
 *
 * @see StaffDAO
 */
public class MySqlStaffDAO extends MySqlBaseDAO implements StaffDAO {
	private static final Logger LOGGER = Logger.getLogger(MySqlStaffDAO.class);

	private Staff getStafFromDB(ResultSet resultSet) throws DaoException {
		Staff staff;
		try {
			staff = new Staff();
			staff.setId(resultSet.getInt("staff_id"));
			staff.setName(resultSet.getString("staff_name"));
			staff.setSurname(resultSet.getString("staff_surname"));
			staff.setProfession(Profession.values()[resultSet.getInt("profession")]);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e);
		}
		return staff;
	}

	@Override
	public Staff read(Integer id) throws DaoException {
		String sql = "SELECT * FROM staff WHERE staff_id =?;";
		Staff staff = null;
		try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				staff = getStafFromDB(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e);
		}
		return staff;
	}

	@Override
	public Staff readBySurname(String surname) throws DaoException {
		String sql = "SELECT * FROM staff WHERE staff_surname =? ORDER BY staff_name;";
		Staff staff = null;
		try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
			statement.setString(1, surname);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				staff = getStafFromDB(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e);
		}
		return staff;
	}

	@Override
	public List<Staff> getStaffs() throws DaoException {
		String sql = "SELECT * FROM staff ORDER BY staff_surname, staff_name;";
		return getListOfStaff(sql);
	}

	private List<Staff> getListOfStaff(String sql) throws DaoException {
		List<Staff> staffList = new ArrayList<>();
		Staff staff;
		try (Statement statement = getConnection().createStatement()) {
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				staff = getStafFromDB(resultSet);
				staffList.add(staff);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e);
		}
		return staffList;
	}

	@Override
	public List<Staff> getActualStaffs() throws DaoException {
		String sql = "SELECT * FROM staff WHERE isBusyOrDeleted=0 ORDER BY staff_surname, staff_name;";
		return getListOfStaff(sql);
	}

	@Override
	public Integer create(Staff staff) throws DaoException {
		String sql = "INSERT INTO staff (staff_name, staff_surname, profession) " + "VALUES (?,?,?);";
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, staff.getName());
			preparedStatement.setString(2, staff.getSurname());
			preparedStatement.setInt(3, staff.getProfession().ordinal());
			preparedStatement.executeUpdate();
			Integer id = null;
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
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
		String sql = "UPDATE staff SET isBusyOrDeleted=1 WHERE staff_id=?;";
		changeToArchive(sql, id);
	}

// delete staff from active crew when delete staff
	@Override
	public void deleteDeletedStaffFromActiveCrew(Integer id) throws DaoException {
		String sql = "DELETE FROM crew WHERE staff_id=?;";
		changeToArchive(sql, id);
	}

	@Override
	public void update(Staff staff) {
		String sql = "UPDATE staff SET staff_name = ?," + "staff_surname = ?, profession = ? WHERE staff_id = ?;";
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, staff.getName());
			preparedStatement.setString(2, staff.getSurname());
			preparedStatement.setInt(3, staff.getProfession().ordinal());
			preparedStatement.setInt(4, staff.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public List<Staff> getStaffsFromCrew(Integer crew_id) throws DaoException {
		String sql = "SELECT staff.* FROM staff,crew,crew_staff "
				+ "WHERE crew.crew_id =? AND crew.crew_id=crew_staff.crew_id AND staff.staff_id = crew_staff.staff_id;";
		List<Staff> staffList = new ArrayList<>();
		Staff staff;
		try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
			preparedStatement.setInt(1, crew_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				staff = getStafFromDB(resultSet);
				staffList.add(staff);
			}
			return staffList;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Staff> getFreeStaffs() throws DaoException {
		String sql = "SELECT staff.* FROM staff WHERE isBusyOrDeleted=0 && staff_id NOT IN (SELECT crew_staff.staff_id FROM crew_staff) ORDER BY staff_surname,staff_name;";
		List<Staff> freeStaff = new ArrayList<>();
		Staff staff;
		try (Statement statement = getConnection().createStatement()) {
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				staff = getStafFromDB(resultSet);
				freeStaff.add(staff);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return freeStaff;
	}
}
