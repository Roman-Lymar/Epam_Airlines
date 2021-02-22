package ua.nure.lymar.airlines.dao;

import java.util.List;

import ua.nure.lymar.airlines.entity.Message;

public interface MessageDAO {

    Integer saveMessage(Message message) throws DaoException;

    List<Message> getMessages() throws DaoException;

    Message getMessageById(Integer id) throws DaoException;

    void updateMessageStatus(Integer id, String status) throws DaoException;
}
