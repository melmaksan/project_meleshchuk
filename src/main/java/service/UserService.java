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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserService {

    private static final UserToOrderService userToOrderService = ServiceFactory.getUserToOrderService();
    private static final UserToServiceService userToService = ServiceFactory.getUserToServiceService();
    private static final OrderService orderService = ServiceFactory.getOrderService();
    private static final ServiceForService serviceForService = ServiceFactory.getServiceService();
    private static final String USER_ALREADY_EXISTS = "Invalid credentials, please try again";
    private static final String SPEC_IS_IN_ORDER = "Specialist is exist in booked order!";
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static UserService instance;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
    }

    public User findUserById(long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            User user = null;
            try {
                user = userDao.findById(userId).orElseThrow(NoSuchFieldException::new);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            List<Service> services = getServices(Objects.requireNonNull(user));
            user.setServices(services);
            return user;
        }
    }

    public User findByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findByLogin(login).orElse(null);
        }
    }

    public User findByName(String firstName, String lastName) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            User user = null;
            try {
                user = userDao.findByName(firstName, lastName).orElseThrow(NoSuchFieldException::new);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            List<Service> services = getServices(Objects.requireNonNull(user));
            user.setServices(services);
            return user;
        }
    }

    public List<User> findAllUser() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            List<User> users = userDao.findAll();
            for (User user : users) {
                List<Service> services = getServices(user);
                user.setServices(services);
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
        for (UserToService userToService : userToServiceList) {
            try {
                services.add(serviceForService.findServiceForOtherEntity(userToService.getServiceId()).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no services here!", ex);
            }
        }
        return services;
    }

    public List<Order> getOrders(User user) {
        List<Order> orders = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findAllOrdersByUser(user.getId());
        for (UserToOrder userToOrder : userToOrders) {
            try {
                orders.add((orderService.findOrderById(userToOrder.getOrderId())));
            } catch (RuntimeException ex) {
                logger.error("There are no orders here!", ex);
            }
        }
        return orders;
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
            switch (Role.RoleIdentifier.valueOf(role)){
                case ADMIN:
                    userDto.setAdminRole();
                    break;
                case SPECIALIST:
                    userDto.setSpecialistRole();
            }
        }
    }

    private List<String> checkUserUniqueness(String login) {
        List<String> errors = new ArrayList<>();
        User user = findByLogin(login);
        if (user != null) {
            errors.add("This login has already booked");
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

    public Optional<User> findUserForOtherEntity(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findById(id);
        }
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
            connection.commit();
        }
        return errors;
    }
}
