package ua.nure.lymar.airlines.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.MessageDAO;
import ua.nure.lymar.airlines.entity.Message;
import ua.nure.lymar.airlines.entity.Severity;
import ua.nure.lymar.airlines.entity.Status;

public class MySqlMessageDAO implements MessageDAO {
    private static final Logger LOGGER = Logger.getLogger(MySqlMessageDAO.class);
    private Connection connection;

    Connection getConnection() {
        LOGGER.debug("connection =  " + connection);
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer saveMessage(Message message) throws DaoException {
        String sql = "INSERT INTO messages (dispatcher_id, severity_id, message_text, issue_date, status) VALUES (?,?,?,?,?);";
        Integer messageId = -1;
        LOGGER.debug("message = " + message);
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, message.getDispatcherId());
            statement.setInt(2, message.getSeverity().getId());
            statement.setString(3, message.getMessageText());
            statement.setDate(4, new java.sql.Date(message.getIssuedDate().getTime()));
            statement.setString(5, Status.WAIT.getStatus());
            statement.executeUpdate();
            sql = "SELECT LAST_INSERT_ID()";
            try (PreparedStatement selectStatement = getConnection().prepareStatement(sql)) {
                messageId = selectStatement.executeQuery().getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return messageId;
    }

    @Override
    public List<Message> getMessages() throws DaoException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * from messages";
        try (PreparedStatement selectStatement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                Severity severity = new Severity();
                severity.setId(resultSet.getInt("severity_id"));
                message.setId(resultSet.getInt("message_id"));
                message.setSeverity(severity);
                message.setMessageText(resultSet.getString("message_text"));
                message.setDispatcherId(resultSet.getInt("dispatcher_id"));
                message.setIssuedDate(resultSet.getDate("issue_date"));
                message.setStatus(Status.fromStatus(resultSet.getString("status")).get());
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return messages;
    }

    @Override
    public Message getMessageById(Integer id) throws DaoException {
        Message message = new Message();
        String sql = "SELECT * from messages where message_id = " + id;
        try (PreparedStatement selectStatement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Severity severity = new Severity();
                severity.setId(resultSet.getInt("severity_id"));
                message.setId(resultSet.getInt("message_id"));
                message.setSeverity(severity);
                message.setMessageText(resultSet.getString("message_text"));
                message.setDispatcherId(resultSet.getInt("dispatcher_id"));
                message.setIssuedDate(resultSet.getDate("issue_date"));
                message.setStatus(Status.fromStatus(resultSet.getString("status")).get());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return message;
    }

    @Override
    public void updateMessageStatus(Integer id, String status) throws DaoException {
        String sql = "update messages set status = ? where message_id = ?";
        try (PreparedStatement updateStatement = getConnection().prepareStatement(sql)) {
            updateStatement.setString(1, status);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
