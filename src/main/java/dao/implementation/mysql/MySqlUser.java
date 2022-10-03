package dao.implementation.mysql;

import dao.abstraction.UserDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserDtoConverter;
import entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlUser implements UserDao {

    private static final String SELECT_ALL =
            "SELECT user.id AS user_id, user.first_name, user.last_name, user.login, user.password, " +
                    "user.email, user.phone_num, user.role_id, role.name AS role_name" +
                    "FROM user" +
                    "JOIN role ON user.role_id = role.id";

    private static final String SELECT_SPECIALIST =
            "SELECT user.id AS user_id, user.first_name, user.last_name, user.rate " +
                    "FROM user";

    private static final String SELECT_USER =
            "SELECT user.id AS user_id, user.first_name, user.last_name, user.email " +
                    "FROM user";

    private static final String WHERE_ID = "WHERE user.id = ? ";

    private static final String WHERE_LOGIN = "WHERE user.login = ? ";

    private static final String WHERE_USER_NAME =
            "WHERE user.first_name = ? AND user.last_name = ? ";

    private static final String ASC_BY_RATING = "ORDER BY rate ASC ";

    private static final String DESC_BY_RATING = "ORDER BY rate DESC ";

    private static final String INSERT =
            "INSERT into user (first_name, last_name, login, password, " +
                    "email, phone_num, rate, role_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE =
            "UPDATE user SET first_name = ?, last_name = ?, login = ?, " +
                    "password = ?, email = ?, phone_num = ? rate = ?, role_id = ? ";

    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? ";

    private static final String UPDATE_RATING = "UPDATE user SET rate = ? ";

    private static final String DELETE = "DELETE FROM user ";

    private static final String PAGINATION = "limit ? offset ? ";

    private static final String NUMBER_OF_ROWS = "SELECT COUNT(*) FROM user ";

    private final DefaultDaoImpl<User> defaultDao;

    public MySqlUser(Connection connection) {
        this(connection, new UserDtoConverter());
    }

    public MySqlUser(Connection connection,
                     DtoConverter<User> converter) {
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
        Objects.requireNonNull(user);
        int id = defaultDao.executeInsertWithGeneratedPrimaryKey(INSERT,
                user.getFirstName(), user.getLastName(), user.getLogin(),
                user.getPassword(), user.getEmail(), user.getPhoneNumber(),
                user.getRating(), user.getRole().getId());
        user.setId(id);
        return user;
    }

    @Override
    public void update(User user) {
        Objects.requireNonNull(user);
        defaultDao.executeUpdate(UPDATE + WHERE_ID, user.getFirstName(),
                user.getLastName(), user.getLogin(), user.getPassword(),
                user.getEmail(), user.getPhoneNumber(), user.getRating(),
                user.getRole().getId());
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
    public Optional<User> findByName(String firstName, String lastName) {
        return defaultDao.findOne(SELECT_ALL + WHERE_USER_NAME, firstName, lastName);
    }

    public Optional<User> findUserByName(String firstName, String lastName) {
        return defaultDao.findOne(SELECT_USER + WHERE_USER_NAME, firstName, lastName);
    }

    public Optional<User> findSpecialistByName(String firstName, String lastName) {
        return defaultDao.findOne(SELECT_SPECIALIST + WHERE_USER_NAME, firstName, lastName);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return defaultDao.findAll(SELECT_ALL + PAGINATION, limit, offset);
    }

    @Override
    public List<User> ascByRating() {
        return defaultDao.findAll(SELECT_SPECIALIST + ASC_BY_RATING);
    }

    @Override
    public List<User> descByRating() {
        return defaultDao.findAll(SELECT_SPECIALIST + DESC_BY_RATING);
    }

    @Override
    public int getNumberOfRows() {
        return defaultDao.getNumberOfRows(NUMBER_OF_ROWS);
    }

    public void changePassword(User user, String password) {
        Objects.requireNonNull(user);
        defaultDao.executeUpdate(UPDATE_PASSWORD + WHERE_ID, password, user.getId());
    }

    public void changeRating(User user, float rate) {
        Objects.requireNonNull(user);
        defaultDao.executeUpdate(UPDATE_RATING + WHERE_ID, rate, user.getId());
    }
}
