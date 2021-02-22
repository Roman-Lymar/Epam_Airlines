package ua.nure.lymar.airlines.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.lymar.airlines.util.Pages;

public class LogoutCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        if (session!=null){
            session.invalidate();
        }
        return Pages.LOGIN_PAGE;
    }
}
