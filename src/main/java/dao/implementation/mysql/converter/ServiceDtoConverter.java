package dao.implementation.mysql.converter;

import entity.Order;
import entity.Service;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceDtoConverter implements DtoConverter<Service> {

    private final static String ID_FIELD = "service_id";
    private final static String SERVICE_NAME = "service_title";
    private final static String SERVICE_DESCRIPTION = "service_description";
    private final static String PRICE = "service_price";

    @Override
    public Service convertToObject(ResultSet resultSet) throws SQLException {
        return Service.newBuilder()
                .addServiceId(resultSet.getLong(ID_FIELD))
                .addServiceName(resultSet.getString(SERVICE_NAME))
                .addDescription(resultSet.getString(SERVICE_DESCRIPTION))
                .addPrice(resultSet.getBigDecimal(PRICE))
                .build();
    }

    private List<User> getSpecialists(String concatSpecialists) {
        return Stream.of(concatSpecialists.split(",")).map(split ->
                User.newBuilder().addFirstName(split).addLastName(split).build()).collect(Collectors.toList());
    }

    private List<Order> getOrders(String concatOrders) {
        return Stream.of(concatOrders.split(",")).map(split ->
                Order.newBuilder().addId(Integer.parseInt(split)).build()).collect(Collectors.toList());
    }
}
