package dao.implementation.mysql;

import dao.abstraction.PaymentStatusDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderStatusDtoConverter;
import dao.implementation.mysql.converter.PaymentStatusDtoConverter;
import entity.OrderStatus;
import entity.PaymentStatus;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlPaymentStatus implements PaymentStatusDao {

    private final static String SELECT_ALL =
            "SELECT id AS payment_status_id, name AS payment_status_name " +
                    "FROM payment_status ";

    private final static String INSERT =
            "INSERT INTO payment_status (name) " +
                    "VALUES(?) ";

    private final static String UPDATE =
            "UPDATE payment_status SET name = ? ";

    private final static String DELETE =
            "DELETE FROM payment_status ";

    private final static String WHERE_ID =
            "WHERE id = ? ";

    private final static String WHERE_NAME =
            "WHERE name = ? ";

    private final DefaultDaoImpl<PaymentStatus> defaultDao;

    public MySqlPaymentStatus(Connection connection) {
        this(connection, new PaymentStatusDtoConverter());
    }

    public MySqlPaymentStatus(Connection connection, DtoConverter<PaymentStatus> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<PaymentStatus> findById(Integer id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<PaymentStatus> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public PaymentStatus insert(PaymentStatus paymentStatus) {
        Objects.requireNonNull(paymentStatus);
        int id = defaultDao.executeInsertWithGeneratedPrimaryKey(INSERT, paymentStatus.getName());
        paymentStatus.setId(id);
        return paymentStatus;
    }

    @Override
    public void update(PaymentStatus paymentStatus) {
        Objects.requireNonNull(paymentStatus);
        defaultDao.executeUpdate(UPDATE + WHERE_ID, paymentStatus.getName(), paymentStatus.getId());
    }

    @Override
    public void delete(Integer id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public Optional<PaymentStatus> findByName(String name) {
        return defaultDao.findOne(SELECT_ALL + WHERE_NAME, name);
    }
}
