package dao.implementation.mysql;

import dao.abstraction.OrderToServiceDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.OrderToServiceDtoConverter;
import entity.OrderToService;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlOrderToService implements OrderToServiceDao {

    private final static String SELECT_ALL =
            "SELECT orders_to_service.orders_id, " +
                    "orders_to_service.service_id, service.title " +
                    "FROM service " +
                    "JOIN orders_to_service ON orders_to_service.service_id = service.id ";

    private final static String WHERE_ORDER_SERVICES =
            "WHERE orders_id = ? AND service_id = ? ";

    private final static String WHERE_ORDER =
            "WHERE orders_id = ? ";

    private final static String WHERE_SERVICE =
            "WHERE service_id = ? ";

    private final static String INSERT =
            "INSERT into orders_to_service (orders_id, service_id ) VALUES(?, ?) ";

    private final static String UPDATE =
            "UPDATE orders_to_service SET orders_id = ?, service_id = ? ";

    private final static String DELETE =
            "DELETE FROM orders_to_service ";

    private static final String EXIST_BY_ORDER =
            "SELECT orders_to_service.service_id FROM included_package " +
                    "WHERE orders_to_service.orders_id = ? ";

    private final DefaultDaoImpl<OrderToService> defaultDao;

    public MySqlOrderToService(Connection connection) {
        this(connection, new OrderToServiceDtoConverter());
    }

    public MySqlOrderToService(Connection connection, DtoConverter<OrderToService> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<OrderToService> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OrderToService> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public OrderToService insert(OrderToService orderToService) {
        Objects.requireNonNull(orderToService);
        defaultDao.executeInsert(INSERT, orderToService.getOrderId(), orderToService.getServiceId());
        return orderToService;
    }

    @Override
    public void update(OrderToService orderToService) {
        Objects.requireNonNull(orderToService);
        defaultDao.executeUpdate(UPDATE + WHERE_ORDER_SERVICES,
                orderToService.getOrderId(), orderToService.getServiceId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ORDER, id);
    }

    @Override
    public boolean exist(Long id) {
        return defaultDao.exist(EXIST_BY_ORDER, id);
    }

    @Override
    public List<OrderToService> findAllByOrder(long orderId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_ORDER, orderId);
    }

    @Override
    public List<OrderToService> findAllByService(long serviceId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_SERVICE, serviceId);
    }
}
