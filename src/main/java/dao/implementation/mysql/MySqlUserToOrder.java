package dao.implementation.mysql;

import dao.abstraction.UserToOrderDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.UserToOrderDtoConverter;
import entity.UserToOrder;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlUserToOrder implements UserToOrderDao {

    private final static String SELECT_ALL =
            "SELECT user_to_orders.user_id, user.first_name, " +
                    "user.last_name, user_to_orders.orders_id " +
                    "FROM user" +
                    "JOIN user_to_orders ON user_to_orders.user_id = user.id ";

    private final static String WHERE_USER_ORDERS =
            "WHERE user_id = ? AND orders_id = ? ";

    private final static String WHERE_USER =
            "WHERE user_id = ? ";

    private final static String WHERE_ORDER =
            "WHERE orders_id = ? ";

    private final static String INSERT =
            "INSERT into user_to_orders (user_id, orders_id ) VALUES(?, ?) ";

    private final static String UPDATE =
            "UPDATE user_to_orders SET user_id = ?, orders_id = ? ";

    private final static String DELETE =
            "DELETE FROM user_to_orders ";

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
        defaultDao.executeUpdate(
                DELETE + WHERE_USER, id);
    }

    @Override
    public List<UserToOrder> findAllByUser(long userId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_USER, userId);
    }

    @Override
    public List<UserToOrder> findAllByOrder(long orderId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_ORDER, orderId);
    }
}
