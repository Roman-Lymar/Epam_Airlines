package ua.nure.lymar.airlines.service;

import java.util.List;

import ua.nure.lymar.airlines.dao.MessageDAO;
import ua.nure.lymar.airlines.entity.Message;

public interface MessageService {

    List<Message> getAllMessages() throws ServiceException;

    Integer saveMessage(Message message) throws ServiceException;

    void setMessageDAO(MessageDAO messageDAO);

    Message getMessageById(Integer id) throws ServiceException;

    void updateMessageStatus(Message message) throws ServiceException;
}
