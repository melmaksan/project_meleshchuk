package dao.implementation.mysql;

import dao.abstraction.PaymentStatusDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.PaymentStatusDtoConverter;
import entity.PaymentStatus;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class MySqlPaymentStatus implements PaymentStatusDao {

    private static final String SELECT_ALL =
            "SELECT id AS payment_status_id, name AS payment_status_name " +
                    "FROM payment_status ";

    private static final String INSERT =
            "INSERT INTO payment_status (id, name) " +
                    "VALUES(?, ?) ";

    private static final String UPDATE =
            "UPDATE payment_status SET name = ? ";

    private static final String DELETE =
            "DELETE FROM payment_status ";

    private static final String WHERE_ID =
            "WHERE id = ? ";

    private static final String WHERE_NAME =
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
        defaultDao.executeInsert(INSERT, paymentStatus.getId(), paymentStatus.getName());
        return paymentStatus;
    }

    @Override
    public void update(PaymentStatus paymentStatus) {
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
