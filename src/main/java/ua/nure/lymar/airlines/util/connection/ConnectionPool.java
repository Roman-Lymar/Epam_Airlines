package ua.nure.lymar.airlines.util.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance = null;
    private int maxPool;
    private DataSource dataSource;
    private List<PooledConnection> free, used;


    private ConnectionPool() throws SQLException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            this.dataSource = (DataSource) envContext.lookup("jdbc/epam_airlines");
            LOGGER.debug("datasource = " + this.dataSource);
            try {
                LOGGER.debug("connection after datasource = " + this.dataSource.getConnection());
            } catch (Exception nup ){
                LOGGER.debug("null pointer " + nup);
            }
            maxPool = 5;
        } catch (NamingException ex) {
            LOGGER.error("Cannot obtain connection ", ex);
        }

        free = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));
        for (int i = 0; i < 4; i++) {
            LOGGER.debug("save connection " + i + ":" + free);
            free.add(createConnectionWrapper());
        }
        used = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));
    }

    public static ConnectionPool getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }

    private synchronized void destroy() {
        for (PooledConnection connection : free) {
            try {
                LOGGER.debug("free connection destroyed " + connection);
                connection.getRawConnection().close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        for (PooledConnection connection : used) {
            try {
                LOGGER.debug("used connection destroyed " + connection);
                connection.getRawConnection().close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        PooledConnection connection;

        if (free.size() > 0) {
            connection = free.remove(free.size() - 1);
            LOGGER.debug("Get free connection " + connection);
        } else if (used.size() < maxPool) {
            connection = createConnectionWrapper();
            LOGGER.debug("Create connection(haven't free) " + connection);
        } else {
            throw new RuntimeException("Can not create connection");
        }

        used.add(connection);
        return connection;
    }

    private PooledConnection createConnectionWrapper() throws SQLException {
        Connection connection = null;
        PooledConnection pooledConnection = null;
        try {
            LOGGER.debug("before get connection " + this.dataSource );
            connection = this.dataSource.getConnection();
            LOGGER.debug("connection = " + connection);
            pooledConnection = new PooledConnection(this, connection);
        } catch (SQLException e) {
            LOGGER.error("Can not create connection", e);
            try {
                if (connection != null) connection.close();
            } catch (SQLException e2) {
                LOGGER.error("Can not close connection", e2);
                throw e;
            }
        }
        LOGGER.debug(connection);
        LOGGER.debug(pooledConnection);
        return pooledConnection;
    }

    void freeConnectionWrapper(PooledConnection connection) {
        used.remove(connection);
        free.add(connection);
        LOGGER.debug("free connection " + connection);
    }
}
