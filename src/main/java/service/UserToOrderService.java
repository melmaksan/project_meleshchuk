package service;

import dao.abstraction.UserToOrderDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;

import entity.UserToOrder;

import java.time.LocalDate;
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

    public List<UserToOrder> findSpecialistsByOrder(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
            return userToOrderDao.findSpecialistsByOrder(orderId);
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

    public List<UserToOrder> findAllBookedByDay(long userId, LocalDate dateFrom, LocalDate dateTo, int status) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
            return userToOrderDao.findAllBookedByDay(userId, dateFrom, dateTo, status);
        }
    }

    public List<UserToOrder> findOrdersByDay(long userId, LocalDate dateFrom, LocalDate dateTo) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
            return userToOrderDao.findOrdersByDay(userId, dateFrom, dateTo);
        }
    }

    public void createUserToOrder(UserToOrder userToOrder , DaoConnection connection) {
        UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
        userToOrderDao.insert(userToOrder);
    }

    public void deleteOrderToUser(long orderId, DaoConnection connection) {
        UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
        userToOrderDao.delete(orderId);
    }

    public void deleteUserToOrder(long userId, DaoConnection connection) {
        UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
        userToOrderDao.deleteUser(userId);
    }

    public boolean isSpecExistsInOrder(long userId, DaoConnection connection) {
        UserToOrderDao userToOrderDao = daoFactory.getUserToOrderDao(connection);
        return userToOrderDao.isSpecExistsInOrder(userId);
    }
}
