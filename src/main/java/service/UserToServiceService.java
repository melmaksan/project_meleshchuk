package service;

import dao.abstraction.UserToServiceDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;

import entity.UserToService;
import java.util.List;

public class UserToServiceService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static UserToServiceService instance;

    public static synchronized UserToServiceService getInstance() {
        if(instance == null) {
            instance = new UserToServiceService();
        }
        return instance;
    }

    private UserToServiceService() {
    }

    public List<UserToService> findAllUsersByService(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToServiceDao userToServiceDao = daoFactory.getUserToServiceDao(connection);
            return userToServiceDao.findAllByService(serviceId);
        }
    }

    public List<UserToService> findAllServicesByUser(long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToServiceDao userToServiceDao = daoFactory.getUserToServiceDao(connection);
            return userToServiceDao.findAllByUser(userId);
        }
    }

    public void createUserToService(UserToService userToService , DaoConnection connection) {
        UserToServiceDao userToServiceDao = daoFactory.getUserToServiceDao(connection);
        userToServiceDao.insert(userToService);
    }

    public void deleteUserToOrder(long userId, DaoConnection connection) {
        UserToServiceDao userToServiceDao = daoFactory.getUserToServiceDao(connection);
        userToServiceDao.delete(userId);
    }
}
