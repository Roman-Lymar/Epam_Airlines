package ua.nure.lymar.airlines.controller.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.lymar.airlines.entity.Users;
import ua.nure.lymar.airlines.entity.Role;
import ua.nure.lymar.airlines.util.Commands;

public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);
    private static final Map<String, Set<Role>> permits = new HashMap<>();

    static {
        Set<Role> all = new HashSet<>();
        all.addAll(Arrays.asList(Role.values()));
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMIN);
        Set<Role> dispetcher = new HashSet<>();
        dispetcher.add(Role.DISPETCHER);

        permits.put(Commands.USERLIST_COMMAND, admin);
        permits.put(Commands.USEREDIT_COMMAND, admin);
        permits.put(Commands.USERSAVE_COMMAND, admin);
        permits.put(Commands.USERDELETE_COMMAND, admin);
        permits.put(Commands.STAFFLIST_COMMAND, dispetcher);
        permits.put(Commands.STAFFEDIT_COMMAND,dispetcher);
        permits.put(Commands.STAFFSAVE_COMMAND,dispetcher);
        permits.put(Commands.STAFFDELETE_COMMAND, dispetcher);
        permits.put(Commands.LOGOUT_COMMAND, all);
        permits.put(Commands.CREWLIST_COMMAND, dispetcher);
        permits.put(Commands.CREWEDIT_COMMAND, dispetcher);
        permits.put(Commands.CREWSAVE_COMMAND, dispetcher);
        permits.put(Commands.CREWSHOW_COMMAND, dispetcher);
        permits.put(Commands.ADDSTAFFINCREW_COMMAND, dispetcher);
        permits.put(Commands.DELSTAFFFROMCREW_COMMAND, dispetcher);
        permits.put(Commands.FLIGHTLIST_COMMAND, all);
        permits.put(Commands.FLIGHTEDIT_COMMAND, all);
        permits.put(Commands.FLIGHTSAVE_COMMAND, all);
        permits.put(Commands.USEREDITPASS_COMMAND, all);
        permits.put(Commands.USERSAVEPASS_COMMAND, all);
        permits.put(Commands.USERSETDEFPASS_COMMAND, admin);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("SecurityFilter init");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        Set<Role> userRoles = permits.get(url);
        if(userRoles != null) {
            HttpSession session = request.getSession(false);
            if(session != null) {
                Users user = (Users)session.getAttribute("currentUser");
                if(user != null && userRoles.contains(user.getUserRole())) {
                    LOGGER.debug("user have permits");
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        } else {
            LOGGER.debug("user need not permits");
            filterChain.doFilter(request, response);
            return;
        }
        LOGGER.info("user have not permits ");
        response.sendRedirect(Commands.LOGIN_COMMAND + "?message=app.access.denied");

    }

    @Override
    public void destroy() {
    }
}
