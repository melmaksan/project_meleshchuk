package service;

import dao.abstraction.OrderToServiceDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.OrderToService;

import java.util.List;

public class OrderToServiceService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static OrderToServiceService instance;

    public static synchronized OrderToServiceService getInstance() {
        if(instance == null) {
            instance = new OrderToServiceService();
        }
        return instance;
    }

    private OrderToServiceService() {
    }

    public List<OrderToService> findAllServicesByOrder(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderToServiceDao orderToServiceDao = daoFactory.getOrderToServiceDao(connection);
            return orderToServiceDao.findAllByOrder(orderId);
        }
    }

    public List<OrderToService> findAllOrdersByService(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderToServiceDao orderToServiceDao = daoFactory.getOrderToServiceDao(connection);
            return orderToServiceDao.findAllByService(serviceId);
        }
    }

    public void createOrderToService(OrderToService orderToService, DaoConnection connection) {
        OrderToServiceDao orderToServiceDao = daoFactory.getOrderToServiceDao(connection);
        orderToServiceDao.insert(orderToService);
    }

    public void deleteOrderToService(long orderId, DaoConnection connection) {
        OrderToServiceDao orderToServiceDao = daoFactory.getOrderToServiceDao(connection);
        orderToServiceDao.delete(orderId);
    }

    public void deleteServiceToOrder(long serviceId, DaoConnection connection) {
        OrderToServiceDao orderToServiceDao = daoFactory.getOrderToServiceDao(connection);
        orderToServiceDao.delete(serviceId);
    }

    public boolean isServiceExistsInOrder(long serviceId, DaoConnection connection) {
        OrderToServiceDao orderToServiceDao = daoFactory.getOrderToServiceDao(connection);
        return orderToServiceDao.isServiceExistInBookedOrder(serviceId);
    }

}
