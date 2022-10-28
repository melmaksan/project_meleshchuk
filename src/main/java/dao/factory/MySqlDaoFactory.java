package dao.factory;

import dao.abstraction.*;
import dao.datasource.PooledConnection;
import dao.exception.DaoException;
import dao.factory.connection.DaoConnection;
import dao.factory.connection.MySqlConnection;
import dao.implementation.mysql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDaoFactory extends DaoFactory {

    private final static String NULLABLE_CONNECTION =
            "No connection!";

    private final static String WRONG_TYPE_CONNECTION =
            "Wrong type connection!";

    private static final Logger logger = LogManager.getLogger(MySqlDaoFactory.class);

    private final DataSource dataSource = PooledConnection.getInstance();

    public DaoConnection getConnection() {
        try {
            return new MySqlConnection(dataSource.getConnection());
        } catch (SQLException ex) {
            logger.error(NULLABLE_CONNECTION, ex);
            throw new DaoException(NULLABLE_CONNECTION, ex);
        }
    }

    private Connection getOwnSqlConnection(DaoConnection connection) {
        checkDaoConnection(connection);
        return (Connection) connection.getNativeConnection();
    }

    private void checkDaoConnection(DaoConnection connection) {
        if (connection == null || connection.getNativeConnection() == null) {
            logger.error(NULLABLE_CONNECTION);
            throw new DaoException(NULLABLE_CONNECTION);
        }
        if (!(connection instanceof MySqlConnection)) {
            logger.error(WRONG_TYPE_CONNECTION);
            throw new DaoException(WRONG_TYPE_CONNECTION);
        }
    }

    @Override
    public OrderDao getOrderDao(DaoConnection connection) {
        return new MySqlOrder(getOwnSqlConnection(connection));
    }

    @Override
    public OrderToServiceDao getOrderToServiceDao(DaoConnection connection) {
        return new MySqlOrderToService(getOwnSqlConnection(connection));
    }

    @Override
    public ServiceDao getServiceDao(DaoConnection connection) {
        return new MySqlService(getOwnSqlConnection(connection));
    }

    @Override
    public UserDao getUserDao(DaoConnection connection) {
        return new MySqlUser(getOwnSqlConnection(connection));
    }

    @Override
    public UserToOrderDao getUserToOrderDao(DaoConnection connection) {
        return new MySqlUserToOrder(getOwnSqlConnection(connection));
    }

    @Override
    public UserToServiceDao getUserToServiceDao(DaoConnection connection) {
        return new MySqlUserToService(getOwnSqlConnection(connection));
    }

    @Override
    public RespondDao getRespondDao(DaoConnection connection) {
        return new MySqlRespond(getOwnSqlConnection(connection));
    }

    @Override
    public UserToRespondDao getUserToRespondDao(DaoConnection connection) {
        return new MySqlUserToRespond(getOwnSqlConnection(connection));
    }
}
