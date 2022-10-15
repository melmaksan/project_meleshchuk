package service;

import dao.abstraction.ServiceDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.OrderStatus;
import entity.Service;
import entity.User;
import entity.UserToService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceForService {


    private static final OrderToServiceService orderToServiceService = ServiceFactory.getOrderToServiceService();
    private static final UserToServiceService userToService = ServiceFactory.getUserToServiceService();
    private static final UserService userService = ServiceFactory.getUserService();
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static ServiceForService instance;
    private static final String SERVICE_IS_IN_ORDER = "service.added.to.order";

    private static final Logger logger = LogManager.getLogger(ServiceForService.class);

    public static synchronized ServiceForService getInstance() {
        if(instance == null) {
            instance = new ServiceForService();
        }
        return instance;
    }

    private ServiceForService() {
    }

    public List<Service> findAllService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.findAll();
            for (Service service : services) {
                List<User> users = getUsers(service);
                service.setUsers(users);
            }
            return services;
        }
    }

    private List<User> getUsers(Service service) {
        List<User> users = new ArrayList<>();
        List<UserToService> userToServiceList = userToService.findAllUsersByService(service.getId());
        for (UserToService userToService : userToServiceList) {
            try {
                users.add((userService.findUserById(userToService.getUserId())).orElse(null));
            } catch (RuntimeException ex){
                logger.error("There are no users here!", ex);
            }
        }
        return users;
    }

    public Optional<Service> findServiceById(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.findById(serviceId);
        }
    }

    public List<Service> ascByPriceService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.ascByPriceService();
        }
    }

    public List<Service> descByPriceService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.descByPriceService();
        }
    }

    public void changePrice(Service service, BigDecimal price) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            serviceDao.changePrice(service, price);
            connection.commit();
        }
    }

    public void createService(Service service) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            serviceDao.insert(service);
            for (User user : service.getUsers()) {
                userToService.createUserToService(
                        createUserToServiceEntity(service, user.getId()), connection);
            }
            connection.commit();
        }
    }

    public UserToService createUserToServiceEntity(Service service, long userId) {
        return UserToService.newBuilder()
                .addUserId(userId)
                .addServiceId(service.getId())
                .build();
    }

    public List<String> deleteService(long serviceId) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (orderToServiceService.isServiceExistsInOrder(serviceId,
                    OrderStatus.StatusIdentifier.BOOKED_STATUS.getId(), connection)) {
                errors.add(SERVICE_IS_IN_ORDER);
                return errors;
            }
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            serviceDao.delete(serviceId);
            connection.commit();
        }
        return errors;
    }

    public int getNumberOfRows() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.getNumberOfRows();
        }
    }
}
