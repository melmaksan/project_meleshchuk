package dao.implementation.mysql;

import dao.abstraction.UserDao;
import dao.datasource.PooledConnection;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserDtoConverter;
import entity.Role;
import entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
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

    private static final String WHERE_ID = "WHERE user.id = ? ";

    private static final String ROLE_ID = "WHERE user.role_id = ? ";

    private static final String WHERE_LOGIN = "WHERE user.login = ? ";

    private static final String WHERE_USER_NAME =
            "WHERE user.first_name = ? AND user.last_name = ? ";

    private static final String ASC_BY_NAME = "ORDER BY first_name ASC ";

    private static final String DESC_BY_NAME = "ORDER BY first_name DESC ";

    private static final String ASC_BY_RATING = "ORDER BY rate ASC ";

    private static final String DESC_BY_RATING = "ORDER BY rate DESC ";

    private static final String INSERT =
            "INSERT into user (first_name, last_name, login, password, " +
                    "phone_num, rate, role_id) VALUES(?, ?, ?, ?, ?, ?, ?) ";

    private static final String UPDATE =
            "UPDATE user SET first_name = ?, last_name = ?, login = ?, " +
                    "password = ?, phone_num = ? ";

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
                user.getPassword(), user.getPhoneNumber(),
                user.getRating(), user.getRole().getId());
        user.setId(id);
        return user;
    }

    @Override
    public void update(User user) {
        Objects.requireNonNull(user);
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
    public Optional<User> findByName(String firstName, String lastName) {
        return defaultDao.findOne(SELECT_ALL + WHERE_USER_NAME, firstName, lastName);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return defaultDao.findAll(SELECT_ALL + PAGINATION, limit, offset);
    }

    @Override
    public List<User> findAllSpecialists() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID,
                Role.RoleIdentifier.SPECIALIST_ROLE.getId());
    }
    @Override
    public List<User> findAllUsers() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID,
                Role.RoleIdentifier.USER_ROLE.getId());
    }

    @Override
    public List<User> ascByRating() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + ASC_BY_RATING,
                Role.RoleIdentifier.SPECIALIST_ROLE.getId());
    }

    @Override
    public List<User> descByRating() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + DESC_BY_RATING,
                Role.RoleIdentifier.SPECIALIST_ROLE.getId());
    }

    @Override
    public List<User> ascByName() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + ASC_BY_NAME,
                Role.RoleIdentifier.SPECIALIST_ROLE.getId());
    }

    @Override
    public List<User> descByName() {
        return defaultDao.findAll(SELECT_ALL + ROLE_ID + DESC_BY_NAME,
                Role.RoleIdentifier.SPECIALIST_ROLE.getId());
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
    public void updateRating(User user, float rate) {
        Objects.requireNonNull(user);
        defaultDao.executeUpdate(UPDATE_RATING + WHERE_ID, rate, user.getId());
    }

    private void printAll(List<User> list) {
        System.out.println("Find all:");
        for (User type : list) {
            System.out.println(type);
        }
    }

    public static void main(String[] args) {
        DataSource dataSource = PooledConnection.getInstance();
        MySqlUser mySqlUser;
//        MySqlRole mySqlRole;

        try {
            mySqlUser = new MySqlUser(dataSource.getConnection());
//            mySqlRole = new MySqlRole(dataSource.getConnection());

            System.out.println("User TEST");

            mySqlUser.printAll(mySqlUser.findAll());

//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Insert test:");
//            User user = mySqlUser.insert(User.newBuilder().addFirstName("AA").addLastName("BB")
//                    .addLogin("CC").addPassword("AAAAAAAAAAA").addPhoneNumber("9379992")
//                    .addDefaultRole().build());
//            mySqlUser.printAll(mySqlUser.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find with id 4:");
            System.out.println(mySqlUser.findById(4L));

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find spec:");
            System.out.println(mySqlUser.findAllSpecialists());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find user:");
            System.out.println(mySqlUser.findAllUsers());
//
//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Find one by name:");
//            System.out.println(mySqlUser.findByName("AA", "BB"));
//
//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Find user:");
//            System.out.println(mySqlUser.findUserByName("AA", "BB", Role.RoleIdentifier.USER_ROLE.getId()));

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Num of rows: ");
            System.out.println(mySqlUser.getNumberOfRows());

//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Change password:");
//            mySqlUser.changePassword(user, "qwerty");
//            mySqlUser.printAll(mySqlUser.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Sort by rate: ");
            System.out.println(mySqlUser.descByRating());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Sort by name: ");
            System.out.println(mySqlUser.ascByName());

//            System.out.println("Update rate:");
//            mySqlUser.updateRating(user, 4f);
//            mySqlUser.printAll(mySqlUser.findAll());
//
//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Delete:");
//            mySqlUser.delete(user.getId());
//            mySqlUser.printAll(mySqlUser.findAll());


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


    }
}
