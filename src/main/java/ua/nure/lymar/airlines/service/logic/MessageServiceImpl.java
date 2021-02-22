package ua.nure.lymar.airlines.service.logic;

import java.util.List;

import ua.nure.lymar.airlines.dao.DaoException;
import ua.nure.lymar.airlines.dao.MessageDAO;
import ua.nure.lymar.airlines.entity.Message;
import ua.nure.lymar.airlines.service.MessageService;
import ua.nure.lymar.airlines.service.ServiceException;

public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO;

    @Override
    public List<Message> getAllMessages() throws ServiceException {
        try {
            return messageDAO.getMessages();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer saveMessage(Message message) throws ServiceException {
        try {
            return messageDAO.saveMessage(message);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setMessageDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public Message getMessageById(Integer id) throws ServiceException {
        try {
            return this.messageDAO.getMessageById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateMessageStatus(Message message) throws ServiceException {
        try {
            this.messageDAO.updateMessageStatus(message.getId(), message.getStatus().getStatus());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
