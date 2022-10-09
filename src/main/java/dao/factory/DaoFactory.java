package dao.factory;

import dao.abstraction.*;

import dao.exception.DaoException;
import dao.factory.connection.DaoConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public abstract class DaoFactory {

    private static final String DB_BUNDLE = "mysqldb";
    private static final String DB_CLASS = "factory.class";
    private static final String ERROR_OBTAIN_INSTANCE =
            "Failed to obtain DaoFactory instance";

    private final static Logger logger = LogManager.getLogger(DaoFactory.class);

    private static DaoFactory instance;

    public static DaoFactory getInstance()  {
        if (instance == null) {
            ResourceBundle bundle = ResourceBundle.getBundle(DB_BUNDLE);
            String className = bundle.getString(DB_CLASS);
            try {
                instance = (DaoFactory) Class.forName(className).
                        getConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                logger.error(ERROR_OBTAIN_INSTANCE, ex);
                throw new DaoException(ERROR_OBTAIN_INSTANCE, ex);
            }
        }
        return instance;
    }

    public abstract DaoConnection getConnection();

    public abstract OrderDao getOrderDao(DaoConnection connection);

    public abstract OrderStatusDao getOrderStatusDao(DaoConnection connection);

    public abstract OrderToServiceDao getOrderToServiceDao(DaoConnection connection);

    public abstract PaymentStatusDao getPaymentStatusDao(DaoConnection connection);

    public abstract RoleDao getRoleDao(DaoConnection connection);

    public abstract ServiceDao getServiceDao(DaoConnection connection);

    public abstract UserDao getUserDao(DaoConnection connection);

    public abstract UserToOrderDao getUserToOrderDao(DaoConnection connection);

    public abstract UserToServiceDao getUserToServiceDao(DaoConnection connection);

    public abstract RespondDao getRespondDao(DaoConnection connection);
}
