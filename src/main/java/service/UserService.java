package service;

import controller.util.Util;
import controller.util.validator.LoginValidator;
import controller.util.validator.PasswordValidator;
import controller.util.validator.PhoneValidator;
import dao.abstraction.UserDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import controller.util.passhash.PasswordStorage;
import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserService {

    private static final UserToRespondService userToRespondService = ServiceFactory.getUserToRespondService();
    private static final UserToOrderService userToOrderService = ServiceFactory.getUserToOrderService();
    private static final UserToServiceService userToService = ServiceFactory.getUserToServiceService();
    private static final OrderService orderService = ServiceFactory.getOrderService();
    private static final ServiceForService serviceForService = ServiceFactory.getServiceService();
    private static final RespondService respondService = ServiceFactory.getRespondService();
    private static final int STATUS = OrderStatus.StatusIdentifier.BOOKED.getId();
    private static final String USER_ALREADY_EXISTS = "Invalid credentials, please try again";
    private static final String SPEC_IS_IN_ORDER = "Specialist is exist in booked order!";
    private static final String LOGIN_IS_EXIST = "There is an account with this email, please " +
            "use another or sign in with it!";
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static UserService instance;

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
    }

    public Optional<User> findUserById(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findById(id);
        }
    }

    public User findByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findByLogin(login).orElse(null);
        }
    }

    public int getNumberOfRows() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.getNumberOfRows();
        }
    }

    public List<User> findAllClientsWithPagination(int limit, int offset) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            List<User> users = userDao.findAll(limit, offset);
            for (User user : users) {
                List<Order> orders = getOrders(user);
                user.setOrders(orders);
            }
            return users;
        }
    }


    public List<User> findAllClients() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            List<User> users = userDao.findAllUsers();
            for (User user : users) {
                List<Service> services = getServices(user);
                user.setServices(services);
            }
            return users;
        }
    }


    public List<User> findAllSpecialists() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            List<User> users = userDao.findAllSpecialists();
            for (User user : users) {
                List<Service> services = getServices(user);
                user.setServices(services);
            }
            return users;
        }
    }

    public List<User> findAdmins() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            List<User> users = userDao.findAllAdmins();
            for (User user : users) {
                List<Service> services = getServices(user);
                user.setServices(services);
            }
            return users;
        }
    }

    private List<Service> getServices(User user) {
        List<Service> services = new ArrayList<>();
        List<UserToService> userToServiceList = userToService.findAllServicesByUser(user.getId());
        for (UserToService us : userToServiceList) {
            services.add(serviceForService.findServiceForOtherEntity(us.getServiceId()).orElse(null));
        }
        return services;
    }

    public List<Order> getOrders(User user) {
        List<Order> orders = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findAllOrdersByUser(user.getId());
        for (UserToOrder userToOrder : userToOrders) {
            orders.add(orderService.findOrderById(userToOrder.getOrderId()));
        }
        return orders;
    }

    public List<Order> getBookedOrdersPerDay(User user, LocalDate start, LocalDate end) {
        List<Order> orders = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findAllBookedByDay(user.getId(), start, end, STATUS);
        for (UserToOrder userToOrder : userToOrders) {
            orders.add(orderService.findOrderById(userToOrder.getOrderId()));
        }
        return orders;
    }

    public List<Order> getOrdersPerDay(User user, LocalDate start, LocalDate end) {
        List<Order> orders = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findOrdersByDay(user.getId(), start, end);
        for (UserToOrder userToOrder : userToOrders) {
            orders.add(orderService.findOrderById(userToOrder.getOrderId()));
        }
        return orders;
    }

    public List<Respond> getResponds(User user) {
        List<Respond> responds = new ArrayList<>();
        List<UserToRespond> userToResponds = userToRespondService.findAllRespondsByUser(user.getId());
        for (UserToRespond userToRespond : userToResponds) {
            responds.add(respondService.findRespondById(userToRespond.getRespondId()));
        }
        return responds;
    }

    private void creatingUser(User user) {
        Objects.requireNonNull(user);
        if (user.getRole() == null) {
            user.setUserRole();
        }
        String hash = PasswordStorage.getSecurePassword(user.getPassword());
        user.setPassword(hash);

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.insert(user);
            connection.commit();
        }
    }

    public boolean isCredentialsValid(String login, String password) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            Optional<User> user = userDao.findByLogin(login);

            return user.filter(u -> PasswordStorage.checkSecurePassword(password,
                    u.getPassword())).isPresent();
        }
    }

    public boolean isUserExists(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.exist(user.getId());
        }
    }

    public List<String> createUser(String firstName, String lastName, String login,
                                   String password, String phone, String role) {
        User userDto = getDataFromRequestCreating(firstName, lastName, login,
                password, phone);
        List<String> errors = validateData(userDto);
        errors.addAll(checkUserUniqueness(login));
        if (errors.isEmpty()) {
            checkRole(role, userDto);
            creatingUser(userDto);
        }
        return errors;
    }

    private void checkRole(String role, User userDto) {
        if (role != null) {
            Role.RoleIdentifier roleIdentifier = Role.RoleIdentifier.valueOf(role);
            if (roleIdentifier == Role.RoleIdentifier.ADMIN) {
                userDto.setAdminRole();
            } else if (roleIdentifier == Role.RoleIdentifier.SPECIALIST) {
                userDto.setSpecialistRole();
            }
        }
    }

    private List<String> checkUserUniqueness(String login) {
        List<String> errors = new ArrayList<>();
        User user = findByLogin(login);
        if (user != null) {
            errors.add(LOGIN_IS_EXIST);
        }
        return errors;
    }

    private User getDataFromRequestCreating(String firstName, String lastName, String login,
                                            String password, String phone) {
        return User.newBuilder()
                .addFirstName(firstName)
                .addLastName(lastName)
                .addLogin(login)
                .addPassword(password)
                .addPhoneNumber(phone)
                .addDefaultRate()
                .build();
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();
        Util.validateField(new LoginValidator(), user.getLogin(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);
        Util.validateField(new PhoneValidator(), user.getPhoneNumber(), errors);
        if (errors.isEmpty() && isUserExists(user)) {
            errors.add(USER_ALREADY_EXISTS);
        }
        return errors;
    }

    public List<String> deleteUser(long userId) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (userToOrderService.isSpecExistsInOrder(userId, connection)) {
                errors.add(SPEC_IS_IN_ORDER);
                return errors;
            }
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.delete(userId);
            userToService.deleteUserToService(userId, connection);
            userToOrderService.deleteUserToOrder(userId, connection);
            connection.commit();
        }
        return errors;
    }

    public void updateRating(User user, double rate) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.updateRating(user, rate);
            connection.commit();
        }
    }

}
