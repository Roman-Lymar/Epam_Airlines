package ua.nure.lymar.airlines.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.entity.Role;
import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.service.ServiceException;
import ua.nure.lymar.airlines.service.UserService;
import ua.nure.lymar.airlines.util.Pages;
import ua.nure.lymar.airlines.util.PasswordToHash;
import ua.nure.lymar.airlines.util.serviceFactory.ServiceFactoryException;

public class LoginCommand extends Command {
	public static final String RUSSIAN = "Russian";
	private final Logger LOGGER = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		if (login != null && password != null) {
			password = PasswordToHash.getHashSha256(password);
			try {
				UserService userService = getServiceFactory().getUserService();
				Users user = userService.readByLogin(login);
				if (user != null && password.equals(user.getPassword())) {
					session.setAttribute("currentUser", user);
					LOGGER.info("user authorized:" + user.getLogin() + " with role: " + user.getUserRole());
					if (user.getUserRole().equals(Role.ADMIN)) {
						return Pages.ADMIN_PAGE;
					} else if (user.getUserRole().equals(Role.DISPETCHER)) {
						return Pages.DISPETCHER_PAGE;
					}
				} else {
					return Pages.LOGIN_PAGE + "?message=app.login.incorrect";
				}
			} catch (ServiceFactoryException | ServiceException e) {
				LOGGER.error(e.getMessage());
				throw new ServletException(e);
			}
		} else
			return Pages.LOGIN_PAGE;
		return null;
	}
}
