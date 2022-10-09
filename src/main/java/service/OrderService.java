package service;

import dao.abstraction.OrderDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



public class OrderService {

    private static final OrderToServiceService orderToServiceService = ServiceFactory.getOrderToServiceService();
    private static final UserToOrderService userToOrderService = ServiceFactory.getUserToOrderService();
    private static final UserService userService = ServiceFactory.getUserService();
    private static final ServiceForService serviceForService = ServiceFactory.getServiceService();
    private static final String ORDER_IS_BOOKED = "Can't delete order, order is booked or is paid";
    private static final String ORDER_IS_PAID = "Order can't be canceled, because order is paid!";
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static OrderService instance;

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public static synchronized OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    private OrderService() {
    }

    public List<Order> findAllService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            List<Order> orders = orderDao.findAll();
            for (Order order : orders) {
                List<User> users = getUsers(order);
                List<Service> services = getServices(order);
                order.setUsers(users);
                order.setServices(services);
            }
            return orders;
        }
    }

    private List<Service> getServices(Order order) {
        List<Service> services = new ArrayList<>();
        List<OrderToService> orderToServices = orderToServiceService.findAllServicesByOrder(order.getId());
        for (OrderToService orderToService : orderToServices) {
            try {
                services.add((serviceForService.findServiceById(orderToService.getServiceId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no services here!", ex);
            }
        }
        return services;
    }

    private List<User> getUsers(Order order) {
        List<User> users = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findAllUsersByOrder(order.getId());
        for (UserToOrder userToOrder : userToOrders) {
            try {
                users.add((userService.findUserById(userToOrder.getUserId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no users here!", ex);
            }
        }
        return users;
    }

    public Optional<Order> findOrderById(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            return orderDao.findById(orderId);
        }
    }

    public int getNumberOfRows() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            return orderDao.getNumberOfRows();
        }
    }

    public void createService(Order order) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.insert(order);
            for (User user : order.getUsers()) {
                userToOrderService.createUserToOrder(
                        createUserToServiceEntity(order, user.getId()), connection);
                for (Service service : order.getServices()) {
                    orderToServiceService.createOrderToService(
                            createOrderToServiceEntity(order, service.getId()), connection);
                }
            }
            connection.commit();
        }
    }

    private OrderToService createOrderToServiceEntity(Order order, long serviceId) {
        return OrderToService.newBuilder()
                .addOrderIdId(order.getId())
                .addServiceId(serviceId)
                .build();
    }

    private UserToOrder createUserToServiceEntity(Order order, long userId) {
        return UserToOrder.newBuilder()
                .addUserId(userId)
                .addOrderId(order.getId())
                .build();
    }

    public List<String> updateOrderStatus(Order order, OrderStatus orderStatus) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (order.getPaymentStatus().getId() == PaymentStatus.PaymentIdentifier.PAID_STATUS.getId() &&
                    orderStatus.getId() == OrderStatus.StatusIdentifier.CANCELED_STATUS.getId()) {
                errors.add(ORDER_IS_PAID);
                return errors;
            }
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.updateOrderStatus(order, orderStatus.getId());
            connection.commit();
        }
        return errors;
    }

    public void updatePaymentStatus(Order order, PaymentStatus paymentStatus) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.updateOrderStatus(order, paymentStatus.getId());
            connection.commit();
        }
    }

    public void changeBookingTime(Order order, LocalDateTime localDateTime) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.changeBookingTime(order, localDateTime);
            connection.commit();
        }
    }

    public List<String> deleteOrder(long orderId) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (orderCheckStatus(orderId) || orderCheckPaymentStatus(orderId)) {
                errors.add(ORDER_IS_BOOKED);
                return errors;
            }
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.delete(orderId);
            connection.commit();
        }
        return errors;
    }

    private boolean orderCheckPaymentStatus(long orderId) {
        return Objects.requireNonNull(findOrderById(orderId).orElse(null)).getPaymentStatus().getId() ==
                PaymentStatus.PaymentIdentifier.PAID_STATUS.getId();
    }

    private boolean orderCheckStatus(long orderId) {
        return Objects.requireNonNull(findOrderById(orderId).orElse(null)).getOrderStatus().getId() ==
                OrderStatus.StatusIdentifier.BOOKED_STATUS.getId();
    }


}
