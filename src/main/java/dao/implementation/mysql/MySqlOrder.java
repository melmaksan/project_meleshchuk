package dao.implementation.mysql;

import dao.abstraction.OrderDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderDtoConverter;
import dao.implementation.mysql.converter.OrderToServiceDtoConverter;
import entity.Order;
import entity.OrderToService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlOrder implements OrderDao {

    private static final String SELECT_ALL =
            "SELECT orders.id, orders.time, orders.status_id, status.name AS status_name, " +
                    "orders.payment_status_id, payment_status.name AS payment_status_name " +
                    "FROM orders " +
                    "JOIN status ON orders.status_id = status.id " +
                    "JOIN payment_status ON orders.payment_status_id = payment_status.id ";

    private static final String WHERE_ID =
            "WHERE orders.id = ? ";

    private static final String INSERT =
            "INSERT into orders (time, status_id, payment_status_id," +
                    "VALUES(?, ?, ?) ";
    private static final String UPDATE =
            "UPDATE orders SET time = ?, status_id = ?, payment_status_id = ? ";

    private static final String DELETE =
            "DELETE FROM orders ";

    private static final String UPDATE_STATUS =
            "UPDATE orders SET status_id = ? ";

    private static final String UPDATE_PAYMENT_STATUS =
            "UPDATE orders SET payment_status_id = ? ";

    private static final String CHANGE_TIME =
            "UPDATE orders SET time = ? ";

    private static final String NUMBER_OF_ROWS = "SELECT COUNT(*) FROM orders";

    private final DefaultDaoImpl<Order> defaultDao;

    public MySqlOrder(Connection connection) {
        this(connection, new OrderDtoConverter());
    }

    public MySqlOrder(Connection connection, DtoConverter<Order> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Order> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public Order insert(Order order) {
        Objects.requireNonNull(order);
        long id = defaultDao.executeInsertWithGeneratedPrimaryKey(INSERT,
                order.getOrderTime(), order.getOrderStatus().getId(), order.getPaymentStatus().getId());
        order.setId(id);
        return order;
    }

    @Override
    public void update(Order order) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(UPDATE + WHERE_ID, order.getOrderTime(),
                order.getOrderStatus().getId(), order.getPaymentStatus().getId(), order.getId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public void changeBookingTime(Order order, LocalDate localDate) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(CHANGE_TIME + WHERE_ID, localDate, order.getId());

    }

    @Override
    public void updateOrderStatus(Order order, long orderStatus) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(UPDATE_STATUS + WHERE_ID, orderStatus, order.getId());
    }

    @Override
    public void updatePaymentStatus(Order order, long paymentStatus) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(UPDATE_PAYMENT_STATUS + WHERE_ID, paymentStatus, order.getId());
    }

    @Override
    public int getNumberOfRows() {
        return defaultDao.getNumberOfRows(NUMBER_OF_ROWS);
    }
}
