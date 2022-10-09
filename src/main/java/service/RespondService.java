package service;

import dao.abstraction.RespondDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Respond;

import java.util.List;
import java.util.Optional;

public class RespondService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static RespondService instance;

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
            return respondDao.findAll();
        }
    }

    public Optional<Respond> findRespondById(long respondId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            RespondDao respondDao = daoFactory.getRespondDao(connection);
            return respondDao.findById(respondId);
        }
    }

    public void deleteRespondById(long includedOptionId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            RespondDao respondDao = daoFactory.getRespondDao(connection);
            respondDao.delete(includedOptionId);
        }
    }

    public void createRespond(String definition, long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            RespondDao respondDao = daoFactory.getRespondDao(connection);
            respondDao.insert(getDataFromRequestCreating(definition, userId));
        }
    }

    private Respond getDataFromRequestCreating(String definition, long userId) {
        return Respond.newBuilder()
                .addRespond(definition)
                .addUserId(userId)
                .build();
    }
}
