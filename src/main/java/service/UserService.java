package service;

import controller.util.Util;
import controller.util.validator.LoginValidator;
import controller.util.validator.PasswordValidator;
import controller.util.validator.PhoneValidator;
import dao.abstraction.UserDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import dao.util.PasswordStorage;
import entity.Order;
import entity.Service;
import entity.User;
import entity.UserToOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static dao.util.Constants.*;

public class UserService {

    private static final UserToOrderService userToOrderService = ServiceFactory.getUserToOrderService();
    private static final OrderService orderService = ServiceFactory.getOrderService();
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static UserService instance;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public static synchronized UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
    }

    public Optional<User> findUserById(long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findById(userId);
        }
    }

    public Optional<User> findByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findByLogin(login);
        }
    }

    public Optional<User> findByName(String firstName, String lastName) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findByName(firstName, lastName);
        }
    }

    public List<User> findAllUser() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findAll();
        }
    }

    public void creatingUser(User user) {
        Objects.requireNonNull(user);

        if (user.getRole() == null) {
            user.setDefaultRole();
        }

        String hash = PasswordStorage.getSecurePassword(user.getPassword());
        user.setPassword(hash);

        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.insert(user);
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


    public List<String> creatingUser(String firstName, String lastName, String login,
                                     String password, String phone) {
        User userDto = getDataFromRequestCreating(firstName, lastName, login,
                password, phone);
        List<String> errors = validateData(userDto);
        if (errors.isEmpty()) {
            creatingUser(userDto);
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


    public List<User> findAll(int limit, int offset) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findAll(limit, offset);
        }
    }

    public int getNumberOfRows() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.getNumberOfRows();
        }
    }

    public void userPagination(HttpServletRequest request) {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<User> list = findAll(recordsPerPage, (page - 1) * recordsPerPage);
        int numberOfRows = getNumberOfRows();
        int numberOfPages = (int) Math.ceil(numberOfRows * 1.0 / recordsPerPage);
        request.setAttribute("users", list);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
    }

    public List<Order> getOrders(User user) {
        List<Order> orders = new ArrayList<>();
        List<UserToOrder> userToOrders = userToOrderService.findAllOrdersByUser(user.getId());
        for (UserToOrder userToOrder : userToOrders) {
            try {
                orders.add((orderService.findOrderById(userToOrder.getOrderId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no orders here!", ex);
            }
        }
        return orders;
    }

}
