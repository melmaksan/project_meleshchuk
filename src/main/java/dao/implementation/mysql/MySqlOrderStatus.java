package dao.implementation.mysql;

import dao.abstraction.OrderStatusDao;
import dao.datasource.PooledConnection;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderStatusDtoConverter;

import entity.Order;
import entity.OrderStatus;
import entity.PaymentStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlOrderStatus implements OrderStatusDao {

    private final static String SELECT_ALL =
            "SELECT id AS status_id, name AS status_name " +
                    "FROM status ";

    private final static String INSERT =
            "INSERT INTO status (id, name) " +
                    "VALUES(?, ?) ";

    private final static String UPDATE =
            "UPDATE status SET name = ? ";

    private final static String DELETE =
            "DELETE FROM status ";

    private final static String WHERE_ID =
            "WHERE id = ? ";

    private final static String WHERE_NAME =
            "WHERE name = ? ";


    private final DefaultDaoImpl<OrderStatus> defaultDao;

    public MySqlOrderStatus(Connection connection) {
        this(connection, new OrderStatusDtoConverter());
    }

    public MySqlOrderStatus(Connection connection, DtoConverter<OrderStatus> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<OrderStatus> findById(Integer id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<OrderStatus> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public OrderStatus insert(OrderStatus orderStatus) {
        Objects.requireNonNull(orderStatus);
        defaultDao.executeInsert(INSERT, orderStatus.getId(), orderStatus.getName());
        return orderStatus;
    }

    @Override
    public void update(OrderStatus orderStatus) {
        Objects.requireNonNull(orderStatus);
        defaultDao.executeUpdate(UPDATE + WHERE_ID, orderStatus.getName(), orderStatus.getId());
    }

    @Override
    public void delete(Integer id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public Optional<OrderStatus> findByName(String name) {
        return defaultDao.findOne(SELECT_ALL + WHERE_NAME, name);
    }

    private void printAll(List<OrderStatus> list) {
        System.out.println("Find all:");
        for (OrderStatus type : list) {
            System.out.println(type);
        }
    }

    public static void main(String[] args) {
        DataSource dataSource = PooledConnection.getInstance();
        MySqlOrderStatus mySqlOrderStatus;

        try {
            mySqlOrderStatus = new MySqlOrderStatus(dataSource.getConnection());

            System.out.println("OrderStatus TEST");

            mySqlOrderStatus.printAll(mySqlOrderStatus.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Insert test:");
            OrderStatus ps = mySqlOrderStatus.insert(new OrderStatus(11, "AAA"));
            mySqlOrderStatus.printAll(mySqlOrderStatus.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find one with id 11:");
            System.out.println(mySqlOrderStatus.findById(11));

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Find one by name AAA:");
            System.out.println(mySqlOrderStatus.findByName("AAA"));

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Update:");
            ps.setName("I am BBB");
            mySqlOrderStatus.update(ps);
            mySqlOrderStatus.printAll(mySqlOrderStatus.findAll());

            System.out.println("~~~~~~~~~~~~");

            System.out.println("Delete:");
            mySqlOrderStatus.delete(ps.getId());
            mySqlOrderStatus.printAll(mySqlOrderStatus.findAll());


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
