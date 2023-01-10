package dao.implementation.mysql;

import dao.abstraction.UserDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserDtoConverter;
import entity.Role;
import entity.User;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer. Implements operations of finding,
 * creating, deleting entities. Account dao layer.
 *
 */

public class MySqlUser implements UserDao {

    private static final String SELECT_ALL =
            "SELECT user.id AS user_id, user.first_name, user.last_name, user.login, user.password, " +
                    "user.phone_num, user.rate, user.role_id, role.name AS role_name " +
                    "FROM user " +
                    "JOIN role ON user.role_id = role.id ";

    private static final String WHERE_ID =
            "WHERE user.id = ? ";

    private static final String ROLE_ID =
            "WHERE user.role_id = ? ";

    private static final String WHERE_LOGIN =
            "WHERE user.login = ? ";

    private static final String ASC_BY_NAME =
            "ORDER BY first_name ASC ";

    private static final String DESC_BY_NAME =
            "ORDER BY first_name DESC ";

    private static final String ASC_BY_RATING =
            "ORDER BY rate ASC ";

    private static final String DESC_BY_RATING =
            "ORDER BY rate DESC ";

    private static final String INSERT =
            "INSERT into user (first_name, last_name, login, password, " +
                    "phone_num, rate, role_id) VALUES(?, ?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE =
            "UPDATE user SET first_name = ?, last_name = ?, login = ?, " +
                    "password = ?, phone_num = ? ";

    private static final String UPDATE_PASSWORD =
            "UPDATE user SET password = ? ";

    private static final String UPDATE_RATING =
            "UPDATE user SET rate = ? ";

    private static final String DELETE =
            "DELETE FROM user ";

    private static final String PAGINATION =
            "limit ? offset ? ";

    private static final String NUMBER_OF_ROWS =
            "SELECT COUNT(*) FROM user ";

    private final DefaultDaoImpl<User> defaultDao;

    public MySqlUser(Connection connection) {
        this(connection, new UserDtoConverter());
    }

    public MySqlUser(Connection connection, DtoConverter<User> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<User> findById(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<User> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public User insert(User user) {
        long id = defaultDao.executeInsertWithGeneratedPrimaryKey(INSERT,
                user.getFirstName(), user.getLastName(), user.getLogin(),
                user.getPassword(), user.getPhoneNumber(),
                user.getRating(), user.getRole().getId());
        user.setId(id);
        return user;
    }

    @Override
    public void update(User user) {
        defaultDao.executeUpdate(UPDATE + WHERE_ID, user.getFirstName(),
                user.getLastName(), user.getLogin(), user.getPassword(),
                user.getPhoneNumber(), user.getId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return defaultDao.findOne(SELECT_ALL + WHERE_LOGIN, login);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return defaultDao.findAll(SELECT_ALL + PAGINATION, limit, offset);
    }

    @Override
    public List<User> findAllSpecialists() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID,
                Role.RoleIdentifier.SPECIALIST.getId());
    }

    @Override
    public List<User> findAllUsers() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID,
                Role.RoleIdentifier.USER.getId());
    }

    @Override
    public List<User> findAllAdmins() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID,
                Role.RoleIdentifier.ADMIN.getId());
    }

    @Override
    public List<User> ascByRating() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + ASC_BY_RATING,
                Role.RoleIdentifier.SPECIALIST.getId());
    }

    @Override
    public List<User> descByRating() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + DESC_BY_RATING,
                Role.RoleIdentifier.SPECIALIST.getId());
    }

    @Override
    public List<User> ascByName() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + ASC_BY_NAME,
                Role.RoleIdentifier.SPECIALIST.getId());
    }

    @Override
    public List<User> descByName() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + DESC_BY_NAME,
                Role.RoleIdentifier.SPECIALIST.getId());
    }

    @Override
    public int getNumberOfRows() {
        return defaultDao.getNumberOfRows(NUMBER_OF_ROWS);
    }

    @Override
    public void changePassword(User user, String password) {
        Objects.requireNonNull(user);
        defaultDao.executeUpdate(UPDATE_PASSWORD + WHERE_ID, password, user.getId());
    }

    @Override
    public void updateRating(User user, double rate) {
        Objects.requireNonNull(user);
        defaultDao.executeUpdate(UPDATE_RATING + WHERE_ID, rate, user.getId());
    }
}
