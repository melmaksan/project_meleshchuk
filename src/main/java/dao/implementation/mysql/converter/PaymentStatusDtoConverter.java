package dao.implementation.mysql.converter;

import entity.PaymentStatus;
import entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentStatusDtoConverter implements DtoConverter<PaymentStatus> {

    private final static String ID_FIELD = "payment_status_id";
    private final static String NAME_FIELD = "payment_status_name";

    @Override
    public PaymentStatus convertToObject(ResultSet resultSet) throws SQLException {
        int paymentStatusId = resultSet.getInt(ID_FIELD);
        String paymentStatusName = resultSet.getString(NAME_FIELD);
        return new PaymentStatus(paymentStatusId, paymentStatusName);
    }
}
