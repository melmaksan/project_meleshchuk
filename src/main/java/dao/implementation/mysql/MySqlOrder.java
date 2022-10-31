package dao.implementation.mysql;

import dao.abstraction.OrderDao;
import dao.datasource.PooledConnection;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderDtoConverter;
import entity.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlOrder implements OrderDao {

    private static final String SELECT_ALL =
            "SELECT orders.id, orders.time, ADDTIME(orders.time, service.duration) as time_end, " +
                    "orders.status_id, status.name AS status_name, orders.payment_status_id, " +
                    "payment_status.name AS payment_status_name " +
                    "FROM orders " +
                    "JOIN status ON orders.status_id = status.id " +
                    "JOIN payment_status ON orders.payment_status_id = payment_status.id " +
                    "JOIN orders_to_service ON orders.id = orders_to_service.orders_id " +
                    "JOIN service ON orders_to_service.service_id = service.id ";

    private static final String WHERE_ID =
            "WHERE orders.id = ? ";

    private static final String INSERT =
            "INSERT into orders (time, status_id, payment_status_id)" +
                    "VALUES(?, ?, ?) ";
    private static final String UPDATE =
            "UPDATE orders SET time = ?, status_id = ?, payment_status_id = ? ";

    private static final String DELETE =
            "DELETE FROM orders ";

    private static final String PAGINATION =
            "limit ? offset ? ";

    private static final String UPDATE_STATUS =
            "UPDATE orders SET status_id = ? ";

    private static final String UPDATE_PAYMENT_STATUS =
            "UPDATE orders SET payment_status_id = ? ";

    private static final String CHANGE_TIME =
            "UPDATE orders SET time = ? ";

    private static final String NUMBER_OF_ROWS = "SELECT COUNT(*) FROM orders";

    private static final String CREDENTIALS =
                    "WHERE orders.time >= ? AND orders.time <= ? AND orders.status_id = ? ";

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
    public List<Order> findAllWithCredentials(LocalDate dateFrom, LocalDate dateTo, int status) {
        return defaultDao.findAll(SELECT_ALL + CREDENTIALS, dateFrom, dateTo, status);
    }

    @Override
    public Order insert(Order order) {
        Objects.requireNonNull(order);
        long id = defaultDao.executeInsertWithGeneratedPrimaryKey(INSERT,
                order.getTimeStart(), order.getOrderStatus().getId(), order.getPaymentStatus().getId());
        order.setId(id);
        return order;
    }

    @Override
    public void update(Order order) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(UPDATE + WHERE_ID, order.getTimeStart(),
                order.getOrderStatus().getId(), order.getPaymentStatus().getId(), order.getId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public void changeBookingTime(Order order, LocalDateTime localDateTime) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(CHANGE_TIME + WHERE_ID, localDateTime, order.getId());

    }

    @Override
    public void updateOrderStatus(Order order, int orderStatus) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(UPDATE_STATUS + WHERE_ID, orderStatus, order.getId());
    }

    @Override
    public void updatePaymentStatus(Order order, int paymentStatus) {
        Objects.requireNonNull(order);
        defaultDao.executeUpdate(UPDATE_PAYMENT_STATUS + WHERE_ID, paymentStatus, order.getId());
    }

    @Override
    public List<Order> findAll(int limit, int offset) {
        return defaultDao.findAll(SELECT_ALL + PAGINATION, limit, offset);
    }

    @Override
    public int getNumberOfRows() {
        return defaultDao.getNumberOfRows(NUMBER_OF_ROWS);
    }

    private void printAll(List<Order> list) {
        System.out.println("Find all:");
        for (Order type : list) {
            System.out.println(type);
        }
    }

    public static void main(String[] args) {
        DataSource dataSource = PooledConnection.getInstance();
        MySqlOrder mySqlOrder;
//        MySqlOrderStatus mySqlOrderStatus;
//        MySqlPaymentStatus mySqlPaymentStatus;

        try {
            mySqlOrder = new MySqlOrder(dataSource.getConnection());
//            mySqlOrderStatus = new MySqlOrderStatus(dataSource.getConnection());
//            mySqlPaymentStatus = new MySqlPaymentStatus(dataSource.getConnection());

            System.out.println("Order TEST");

            mySqlOrder.printAll(mySqlOrder.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("find TEST");
            mySqlOrder.printAll(mySqlOrder
                    .findAllWithCredentials(LocalDate.now().plusDays(2),
                            LocalDate.now().plusDays(7),  2));

//            System.out.println("Insert test:");
//            Order order = mySqlOrder.insert(Order.newBuilder().addOrderTime(LocalDateTime.now())
//                    .addDefaultStatus().addDefaultPaymentStatus().build());
//            mySqlOrder.printAll(mySqlOrder.findAll());
//
//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Change day:");
//            mySqlOrder.changeBookingTime(order, LocalDateTime.of(2022, 8, 26, 13, 45));
//            mySqlOrder.printAll(mySqlOrder.findAll());

//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Change status:");
//            mySqlOrder.updateOrderStatus(order, OrderStatus.StatusIdentifier.DONE_STATUS.getId());
//            mySqlOrder.printAll(mySqlOrder.findAll());
//
//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Change status:");
//            mySqlOrder.updatePaymentStatus(order, PaymentStatus.PaymentIdentifier.PAID_STATUS.getId());
//            mySqlOrder.printAll(mySqlOrder.findAll());

//            System.out.println("~~~~~~~~~~~~");
//
//            System.out.println("Delete:");
//            mySqlOrder.delete(order.getId());
//            mySqlOrder.printAll(mySqlOrder.findAll());



        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
