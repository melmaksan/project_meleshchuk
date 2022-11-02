package dao.implementation.mysql;

import dao.abstraction.UserToServiceDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserToServiceDtoConverter;
import entity.UserToService;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlUserToService implements UserToServiceDao {

    private static final String SELECT_ALL =
            "SELECT user_to_service.user_id, user.first_name, user.last_name, " +
                    "user.rate, user_to_service.service_id, service.title " +
                    "FROM user " +
                    "JOIN user_to_service ON user_to_service.user_id = user.id " +
                    "JOIN service ON user_to_service.service_id = service.id ";

    private static final String WHERE_USER_SERVICES =
            "WHERE user_id = ? AND service_id = ? ";

    private static final String WHERE_USER =
            "WHERE user_id = ? ";

    private static final String WHERE_SERVICE =
            "WHERE service_id = ? ";

    private static final String INSERT =
            "INSERT into user_to_service (user_id, service_id ) VALUES(?, ?) ";

    private static final String UPDATE =
            "UPDATE user_to_service SET user_id = ?, service_id = ? ";

    private static final String DELETE =
            "DELETE FROM user_to_service ";

    private final DefaultDaoImpl<UserToService> defaultDao;

    public MySqlUserToService(Connection connection) {
        this(connection, new UserToServiceDtoConverter());
    }

    public MySqlUserToService(Connection connection, DtoConverter<UserToService> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<UserToService> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserToService> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public UserToService insert(UserToService userToService) {
        Objects.requireNonNull(userToService);
        defaultDao.executeInsert(INSERT, userToService.getUserId(), userToService.getServiceId());
        return userToService;
    }

    @Override
    public void update(UserToService userToService) {
        Objects.requireNonNull(userToService);
        defaultDao.executeUpdate(UPDATE + WHERE_USER_SERVICES,
                userToService.getUserId(), userToService.getServiceId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_USER, id);
    }

    @Override
    public void deleteService(long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_SERVICE, id);
    }

    @Override
    public List<UserToService> findAllByUser(long userId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER, userId);
    }

    @Override
    public List<UserToService> findAllByService(long serviceId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_SERVICE, serviceId);
    }
}
