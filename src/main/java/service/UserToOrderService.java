package service;

import dao.abstraction.UserToOrderDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;

import entity.UserToOrder;
import java.util.List;

public class UserToOrderService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static UserToOrderService instance;

    public static synchronized UserToOrderService getInstance() {
        if(instance == null) {
            instance = new UserToOrderService();
        }
        return instance;
    }

    private UserToOrderService() {
    }

    public List<UserToOrder> findAllUsersByOrder(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
            return userToOrderDao.findAllByOrder(orderId);
        }
    }

    public List<UserToOrder> findSpecialistByOrder(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
            return userToOrderDao.findSpecialistByOrder(orderId);
        }
    }

    public List<UserToOrder> findClientsByOrder(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
            return userToOrderDao.findClientsByOrder(orderId);
        }
    }

    public List<UserToOrder> findAllOrdersByUser(long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
            return userToOrderDao.findAllByUser(userId);
        }
    }

    public void createUserToOrder(UserToOrder userToOrder , DaoConnection connection) {
        UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
        userToOrderDao.insert(userToOrder);
    }

    public void deleteUserToOrder(long userId, DaoConnection connection) {
        UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
        userToOrderDao.delete(userId);
    }

    public boolean isSpecExistsInOrder(long userId, DaoConnection connection) {
        UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
        return userToOrderDao.isServiceExistInBookedOrder(userId);
    }
}
