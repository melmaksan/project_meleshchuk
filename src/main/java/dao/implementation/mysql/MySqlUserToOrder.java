package dao.implementation.mysql;

import dao.abstraction.UserToOrderDao;
import dao.datasource.PooledConnection;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserToOrderDtoConverter;
import entity.OrderStatus;
import entity.Role;
import entity.UserToOrder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlUserToOrder implements UserToOrderDao {

    private static final String SELECT_ALL =
            "SELECT user_to_orders.user_id, user.first_name, " +
                    "user.last_name, user_to_orders.orders_id " +
                    "FROM user " +
                    "JOIN user_to_orders ON user_to_orders.user_id = user.id ";

    private static final String SELECT_ALL_FOR_SPEC =
            "SELECT user_to_orders.user_id, user_to_orders.orders_id, orders.time " +
                    "FROM orders " +
                    "JOIN user_to_orders ON user_to_orders.orders_id = orders.id " +
                    "WHERE user_id = ? AND orders.time >= ? AND orders.time <= ? ";

    private static final String WHERE_STATUS =
            "AND status_id = ? ";

    private static final String WHERE_USER_ORDERS =
            "WHERE user_id = ? AND orders_id = ? ";

    private static final String WHERE_USER_IS_SPECIALIST =
            "WHERE orders_id = ? AND user.role_id = ? ";

    private static final String WHERE_USER =
            "WHERE user_id = ? ";

    private static final String WHERE_ORDER =
            "WHERE orders_id = ? ";

    private static final String INSERT =
            "INSERT into user_to_orders (user_id, orders_id ) VALUES(?, ?) ";

    private static final String UPDATE =
            "UPDATE user_to_orders SET user_id = ?, orders_id = ? ";

    private static final String DELETE =
            "DELETE FROM user_to_orders ";

    private static final String EXIST_BY_SERVICE =
            "SELECT user_to_orders.orders_id " +
                    "FROM user_to_orders " +
                    "JOIN orders ON user_to_orders.orders_id = orders.id " +
                    "WHERE user_to_orders.user_id = ? AND orders.status_id = ?";

    private final DefaultDaoImpl<UserToOrder> defaultDao;

    public MySqlUserToOrder(Connection connection) {
        this(connection, new UserToOrderDtoConverter());
    }

    public MySqlUserToOrder(Connection connection, DtoConverter<UserToOrder> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<UserToOrder> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserToOrder> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public UserToOrder insert(UserToOrder userToOrder) {
        Objects.requireNonNull(userToOrder);
        defaultDao.executeInsert(INSERT, userToOrder.getUserId(), userToOrder.getOrderId());
        return userToOrder;
    }

    @Override
    public void update(UserToOrder userToOrder) {
        Objects.requireNonNull(userToOrder);
        defaultDao.executeUpdate(UPDATE + WHERE_USER_ORDERS,
                userToOrder.getUserId(), userToOrder.getOrderId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_ORDER, id);
    }

    @Override
    public void deleteUser(long id) {
        defaultDao.executeUpdate(DELETE + WHERE_USER, id);
    }

    @Override
    public List<UserToOrder> findAllByUser(long userId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER, userId);
    }

    @Override
    public List<UserToOrder> findAllByOrder(long orderId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_ORDER, orderId);
    }

    @Override
    public List<UserToOrder> findSpecialistsByOrder(long orderId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER_IS_SPECIALIST, orderId,
                Role.RoleIdentifier.SPECIALIST.getId());
    }

    @Override
    public List<UserToOrder> findClientsByOrder(long orderId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER_IS_SPECIALIST, orderId,
                Role.RoleIdentifier.USER.getId());
    }

    @Override
    public List<UserToOrder> findAllBookedByDay(long userId, LocalDate dateFrom, LocalDate dateTo, int status) {
        return defaultDao.findAll(SELECT_ALL_FOR_SPEC + WHERE_STATUS, userId, dateFrom, dateTo, status);
    }

    @Override
    public List<UserToOrder> findOrdersByDay(long userId, LocalDate dateFrom, LocalDate dateTo) {
        return defaultDao.findAll(SELECT_ALL_FOR_SPEC, userId, dateFrom, dateTo);
    }

    @Override
    public boolean isSpecExistsInOrder(long userId) {
        return defaultDao.exist(EXIST_BY_SERVICE, userId, OrderStatus.StatusIdentifier.BOOKED.getId());
    }

    private void printAll(List<UserToOrder> list) {
        System.out.println("Find all:");
        for (UserToOrder type : list) {
            System.out.println(type);
        }
    }

    public static void main(String[] args) {
        DataSource dataSource = PooledConnection.getInstance();
        MySqlUserToOrder mySqlUserToOrder;

        try {
            mySqlUserToOrder = new MySqlUserToOrder(dataSource.getConnection());

            System.out.println("Order TEST");

            mySqlUserToOrder.printAll(mySqlUserToOrder.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("find spec: ");
            System.out.println(mySqlUserToOrder.findSpecialistsByOrder(10));

            System.out.println("~~~~~~~~~~~~");

            System.out.println(mySqlUserToOrder.findAllByOrder(11));

            System.out.println("~~~~~~~~~~~~");

            System.out.println(mySqlUserToOrder.findAllByUser(9));

            System.out.println("~~~~~~~~~~~~");

            System.out.println(mySqlUserToOrder.findAllBookedByDay(2, LocalDate.now(),
                    LocalDate.now().plusDays(6), 1));

            System.out.println("~~~~~~~~~~~~");

            System.out.println(mySqlUserToOrder.findOrdersByDay(2, LocalDate.now(),
                    LocalDate.now().plusDays(6)));

            System.out.println("~~~~~~~~~~~~");

            System.out.println(mySqlUserToOrder.isSpecExistsInOrder(7));

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
