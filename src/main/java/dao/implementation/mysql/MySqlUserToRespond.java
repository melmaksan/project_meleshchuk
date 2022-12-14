package dao.implementation.mysql;

import dao.abstraction.UserToRespondDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserToRespondDtoConverter;
import entity.Role;
import entity.UserToRespond;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlUserToRespond implements UserToRespondDao {

    private static final String SELECT_ALL =
            "SELECT user_to_responds.user_id, user.first_name, user.last_name, " +
                    "user_to_responds.respond_id " +
                    "FROM user " +
                    "JOIN user_to_responds ON user_to_responds.user_id = user.id ";

    private static final String WHERE_USER_RESPONDS =
            "WHERE user_id = ? AND respond_id = ? ";

    private static final String WHERE_USER_IS_SPECIALIST =
            "WHERE respond_id = ? AND user.role_id = ? ";

    private static final String WHERE_USER =
            "WHERE user_id = ? ";

    private static final String WHERE_RESPOND =
            "WHERE respond_id = ? ";

    private static final String INSERT =
            "INSERT into user_to_responds (user_id, respond_id ) VALUES(?, ?) ";

    private static final String UPDATE =
            "UPDATE user_to_responds SET user_id = ?, respond_id = ? ";

    private static final String DELETE =
            "DELETE FROM user_to_responds ";



    private final DefaultDaoImpl<UserToRespond> defaultDao;

    public MySqlUserToRespond(Connection connection) {this(connection, new UserToRespondDtoConverter());}

    public MySqlUserToRespond(Connection connection, DtoConverter<UserToRespond> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<UserToRespond> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserToRespond> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public UserToRespond insert(UserToRespond userToRespond) {
        Objects.requireNonNull(userToRespond);
        defaultDao.executeInsert(INSERT, userToRespond.getUserId(), userToRespond.getRespondId());
        return userToRespond;
    }

    @Override
    public void update(UserToRespond userToRespond) {
        Objects.requireNonNull(userToRespond);
        defaultDao.executeUpdate(UPDATE + WHERE_USER_RESPONDS,
                userToRespond.getUserId(), userToRespond.getRespondId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_USER, id);
    }

    @Override
    public List<UserToRespond> findAllByUser(long userId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER, userId);
    }

    @Override
    public List<UserToRespond> findAllByRespond(long respondId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_RESPOND, respondId);
    }

    @Override
    public List<UserToRespond> findSpecialistsByRespond(long respondId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER_IS_SPECIALIST, respondId,
                Role.RoleIdentifier.SPECIALIST.getId());
    }

    @Override
    public List<UserToRespond> findClientsByRespond(long respondId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER_IS_SPECIALIST, respondId,
                Role.RoleIdentifier.USER.getId());
    }
}
