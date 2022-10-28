package service;

import dao.abstraction.RespondDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Respond;
import entity.User;
import entity.UserToRespond;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RespondService {

    private static final UserToRespondService userToRespondService = ServiceFactory.getUserToRespondService();
    private static final UserService userService = ServiceFactory.getUserService();

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static RespondService instance;

    private static final Logger logger = LogManager.getLogger(RespondService.class);

    public static synchronized RespondService getInstance() {
        if (instance == null) {
            instance = new RespondService();
        }
        return instance;
    }

    private RespondService() {
    }

    public List<Respond> findAllRespond() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            RespondDao respondDao = daoFactory.getRespondDao(connection);
            List<Respond> responds = respondDao.findAll();
            for (Respond respond : responds) {
                List<User> users = getClients(respond);
                List<User> specialists = getSpecialist(respond);
                respond.setUsers(users);
                respond.setSpecialists(specialists);
            }
            return responds;
        }
    }

    private List<User> getSpecialist(Respond respond) {
        List<User> specialists = new ArrayList<>();
        List<UserToRespond> userToResponds = userToRespondService.findSpecialistByRespond(respond.getId());
        for (UserToRespond userToRespond : userToResponds) {
            try {
                specialists.add((userService.findUserById(userToRespond.getUserId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no specialists here!", ex);
            }
        }
        return specialists;
    }

    private List<User> getClients(Respond respond) {
        List<User> clients = new ArrayList<>();
        List<UserToRespond> userToResponds = userToRespondService.findClientsByRespond(respond.getId());
        for (UserToRespond userToRespond : userToResponds) {
            try {
                clients.add((userService.findUserById(userToRespond.getUserId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no clients here!", ex);
            }
        }
        return clients;
    }

    public Respond findRespondById(long respondId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            RespondDao respondDao = daoFactory.getRespondDao(connection);
            Respond respond = respondDao.findById(respondId).orElse(null);
            List<User> users = getClients(Objects.requireNonNull(respond));
            List<User> specialists = getSpecialist(respond);
            respond.setUsers(users);
            respond.setSpecialists(specialists);
            return respond;
        }
    }

    public void deleteRespondById(long includedOptionId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            RespondDao respondDao = daoFactory.getRespondDao(connection);
            respondDao.delete(includedOptionId);
            connection.commit();
        }
    }

    public void createRespond(String name, LocalDateTime dateTime, int mark, String message, long userId, long specId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            RespondDao respondDao = daoFactory.getRespondDao(connection);
            Respond respond = respondDao.insert(getDataFromRequestCreating(name, dateTime, mark, message));
            userToRespondService.createUserToRespond(
                    createUserToRespondEntity(respond.getId(), userId), connection);
            userToRespondService.createUserToRespond(
                    createUserToRespondEntity(respond.getId(), specId), connection);
            connection.commit();
        }
    }

    private Respond getDataFromRequestCreating(String name, LocalDateTime dateTime, int mark, String message) {
        return Respond.newBuilder()
                .addName(name)
                .addRespondTime(dateTime)
                .addMark(mark)
                .addRespond(message)
                .build();
    }

    private UserToRespond createUserToRespondEntity(long respondId, long userId) {
        return UserToRespond.newBuilder()
                .addRespondId(respondId)
                .addUserId(userId)
                .build();
    }
}
