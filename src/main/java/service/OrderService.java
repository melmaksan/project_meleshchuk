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
    private static final String ORDER_IS_BOOKED = "Can't delete the order, it has booked or paid! " +
            "You can cancel the order and try again";
    private static final String ORDER_IS_NOT_BOOKED = "You can't change order time because order has already done or canceled!";
    private static final String ORDER_IS_PAID = "Order can't be canceled because order has paid!";
    private static final String ORDER_PAID = "You can't change payment status because order has already paid or " +
            "the order is canceled and you can`t paid it!";

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
                List<User> users = getClients(order);
                List<User> specialists = getSpecialist(order);
                List<Service> services = getServices(order);
                order.setUsers(users);
                order.setSpecialists(specialists);
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

    private List<User> getClients(Order order) {
        List<User> specialists = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findClientsByOrder(order.getId());
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
            List<User> specialists = getSpecialist(order);
            List<User> users = getClients(order);
            order.setSpecialists(specialists);
            order.setServices(serviceList);
            order.setUsers(users);
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
        return Order.newBuilder()
                .addOrderTime(LocalDateTime.parse(dateTime))
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

    public List<String> updateOrderStatus(Order order, int orderStatus) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (checkPaymentStatus(order.getPaymentStatus().getId(), PaymentStatus.PaymentIdentifier.PAID) &&
                    checkOrderStatus(orderStatus, OrderStatus.StatusIdentifier.CANCELED)) {
                errors.add(ORDER_IS_PAID);
                return errors;
            }
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.updateOrderStatus(order, orderStatus);
            connection.commit();
        }
        return errors;
    }

    public List<String> updatePaymentStatus(Order order, int paymentStatus) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if ((checkPaymentStatus(order.getPaymentStatus().getId(), PaymentStatus.PaymentIdentifier.PAID)
                    && checkPaymentStatus(paymentStatus, PaymentStatus.PaymentIdentifier.UNPAID)) ||
                    checkOrderStatus(order.getOrderStatus().getId(), OrderStatus.StatusIdentifier.CANCELED)) {
                errors.add(ORDER_PAID);
                return errors;
            }
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.updatePaymentStatus(order, paymentStatus);
            connection.commit();
        }
        return errors;
    }

    private boolean checkPaymentStatus(int id, PaymentStatus.PaymentIdentifier paid) {
        return id == paid.getId();
    }

    private boolean checkOrderStatus(int id, OrderStatus.StatusIdentifier canceled) {
        return id == canceled.getId();
    }

    public List<String> changeBookingTime(Order order, String dateTime) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (checkOrderStatus(order.getOrderStatus().getId(), OrderStatus.StatusIdentifier.CANCELED) ||
                    checkOrderStatus(order.getOrderStatus().getId(), OrderStatus.StatusIdentifier.DONE)) {
                errors.add(ORDER_IS_NOT_BOOKED);
                return errors;
            }
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            orderDao.changeBookingTime(order, LocalDateTime.parse(dateTime));
            connection.commit();
        }
        return errors;
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
        return (checkPaymentStatus(Objects.requireNonNull(findOrderById(orderId)).getPaymentStatus().getId(),
                PaymentStatus.PaymentIdentifier.PAID)) &&
                (checkOrderStatus(Objects.requireNonNull(findOrderById(orderId)).getOrderStatus().getId(),
                        OrderStatus.StatusIdentifier.BOOKED));
    }

    private boolean orderCheckStatus(long orderId) {
        return checkOrderStatus(Objects.requireNonNull(findOrderById(orderId)).getOrderStatus().getId(),
                OrderStatus.StatusIdentifier.BOOKED);
    }

    public Optional<Order> findOrderForOtherEntity(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.getOrderDao(connection);
            return orderDao.findById(id);
        }
    }

}
