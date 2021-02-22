package ua.nure.lymar.airlines.controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class CharsetEncodingFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(CharsetEncodingFilter.class);

    private String encoding;

    public void init(FilterConfig filterConfig) {
        LOGGER.debug("Filter init");
        encoding = filterConfig.getInitParameter("encoding");
        LOGGER.trace("Encoding ->" + encoding);
        LOGGER.debug("Filter init finished");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("Filter starts");
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        LOGGER.debug("Filter finished with encoding - " + encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        LOGGER.debug("Filter destroy");
    }
}
