package dao.implementation.mysql;

import dao.abstraction.OrderStatusDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderStatusDtoConverter;

import entity.OrderStatus;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlOrderStatus implements OrderStatusDao {

    private static final String SELECT_ALL =
            "SELECT id AS status_id, name AS status_name " +
                    "FROM status ";

    private static final String INSERT =
            "INSERT INTO status (id, name) " +
                    "VALUES(?, ?) ";

    private static final String UPDATE =
            "UPDATE status SET name = ? ";

    private static final String DELETE =
            "DELETE FROM status ";

    private static final String WHERE_ID =
            "WHERE id = ? ";

    private static final String WHERE_NAME =
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
}
