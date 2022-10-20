package service;

import dao.abstraction.OrderDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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

    public List<Order> findAllOrders() {
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
                services.add((serviceForService.findServiceForOtherEntity(orderToService.getServiceId())).orElse(null));
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
                users.add((userService.findUserForOtherEntity(userToOrder.getUserId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no users here!", ex);
            }
        }
        return users;
    }

    private List<User> getSpecialist(Order order) {
        List<User> specialists = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findSpecialistByOrder(order.getId());
        for (UserToOrder userToOrder : userToOrders) {
            try {
                specialists.add((userService.findUserForOtherEntity(userToOrder.getUserId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no users here!", ex);
            }
        }
        return specialists;
    }

    public Order findOrderById(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            Order order = null;
            try {
                order = orderDao.findById(orderId).orElseThrow(NoSuchFieldException::new);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            List<Service> serviceList = getServices(Objects.requireNonNull(order));
            List<User> users = getSpecialist(order);
            order.setUsers(users);
            order.setServices(serviceList);
            return order;
        }
    }

    public void createOrder(String dataTime, long userId, long specId, long serviceId) {
        Order orderDto = getDataFromRequestCreating(dataTime);
        Objects.requireNonNull(orderDto);
        if (orderDto.getOrderStatus() == null) {
            orderDto.setDefaultOrderStatus();
        }
        if (orderDto.getOrderStatus() == null) {
            orderDto.setDefaultPaymentStatus();
        }
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.insert(orderDto);
            userToOrderService.createUserToOrder(
                    createUserToOrderEntity(orderDto.getId(), userId), connection);
            userToOrderService.createUserToOrder(
                    createUserToOrderEntity(orderDto.getId(), specId), connection);
            orderToServiceService.createOrderToService(
                    createOrderToServiceEntity(orderDto.getId(), serviceId), connection);
            connection.commit();
        }
    }


    private Order getDataFromRequestCreating(String dateTime) {
        String dateTimeString = dateTime.replace("T", " ");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return Order.newBuilder()
                .addOrderTime(LocalDateTime.parse(dateTimeString, dateTimeFormatter))
                .addDefaultStatus()
                .addDefaultPaymentStatus()
                .build();
    }

    private OrderToService createOrderToServiceEntity(long orderId, long serviceId) {
        return OrderToService.newBuilder()
                .addOrderId(orderId)
                .addServiceId(serviceId)
                .build();
    }

    private UserToOrder createUserToOrderEntity(long orderId, long userId) {
        return UserToOrder.newBuilder()
                .addUserId(userId)
                .addOrderId(orderId)
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
        return Objects.requireNonNull(findOrderById(orderId)).getPaymentStatus().getId() ==
                PaymentStatus.PaymentIdentifier.PAID_STATUS.getId();
    }

    private boolean orderCheckStatus(long orderId) {
        return Objects.requireNonNull(findOrderById(orderId)).getOrderStatus().getId() ==
                OrderStatus.StatusIdentifier.BOOKED_STATUS.getId();
    }

    public Optional<Order> findOrderForOtherEntity(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            return orderDao.findById(id);
        }
    }

}
