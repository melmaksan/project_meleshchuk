package service;

import dao.abstraction.UserToOrderDao;
import dao.abstraction.UserToRespondDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.UserToOrder;
import entity.UserToRespond;

import java.util.List;

public class UserToRespondService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static UserToRespondService instance;

    public static synchronized UserToRespondService getInstance() {
        if(instance == null) {
            instance = new UserToRespondService();
        }
        return instance;
    }

    private UserToRespondService() {
    }

    public List<UserToRespond> findSpecialistByRespond(long respondId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToRespondDao userToOrderDao = daoFactory.getUserToRespondDao(connection);
            return userToOrderDao.findSpecialistByRespond(respondId);
        }
    }

    public List<UserToRespond> findClientsByRespond(long respondId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToRespondDao userToOrderDao = daoFactory.getUserToRespondDao(connection);
            return userToOrderDao.findClientsByRespond(respondId);
        }
    }

    public List<UserToRespond> findAllRespondsByUser(long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserToRespondDao userToOrderDao = daoFactory.getUserToRespondDao(connection);
            return userToOrderDao.findAllByUser(userId);
        }
    }

    public void createUserToRespond(UserToRespond userToRespond , DaoConnection connection) {
        UserToRespondDao userToRespondDao = daoFactory.getUserToRespondDao(connection);
        userToRespondDao.insert(userToRespond);
    }

    public void deleteUserToRespond(long userId, DaoConnection connection) {
        UserToRespondDao userToRespondDao = daoFactory.getUserToRespondDao(connection);
        userToRespondDao.delete(userId);
    }
}
