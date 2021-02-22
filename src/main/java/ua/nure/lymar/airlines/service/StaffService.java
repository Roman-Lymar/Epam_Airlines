package ua.nure.lymar.airlines.service;

import java.util.List;

import ua.nure.lymar.airlines.dao.StaffDAO;
import ua.nure.lymar.airlines.entity.Staff;

public interface StaffService {
    Staff readById(Integer id) throws ServiceException;

    Staff readByLastname(String surname) throws ServiceException;

    List<Staff> readStaffs() throws ServiceException;

    List<Staff> readActualStaffs() throws ServiceException;

    List<Staff> readStaffFromCrew(Integer id) throws ServiceException;

    void save(Staff staff) throws ServiceException;

    void delete(Integer id) throws ServiceException;

    void setStaffDAO(StaffDAO staffDAO);
}
