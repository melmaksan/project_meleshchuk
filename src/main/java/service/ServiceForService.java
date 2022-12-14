package service;

import dao.abstraction.ServiceDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Service;
import entity.User;
import entity.UserToService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceForService {

    private static final OrderToServiceService orderToServiceService = ServiceFactory.getOrderToServiceService();
    private static final UserToServiceService userToService = ServiceFactory.getUserToServiceService();
    private static final UserService userService = ServiceFactory.getUserService();
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static ServiceForService instance;
    private static final String SERVICE_IS_IN_ORDER = "Service is exist in booked order!";

    private static final Logger logger = LogManager.getLogger(ServiceForService.class);

    public static synchronized ServiceForService getInstance() {
        if (instance == null) {
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
                List<User> specialists = getSpecialists(service);
                service.setUsers(specialists);
            }
            return services;
        }
    }

    public List<User> getSpecialists(Service service) {
        List<User> users = new ArrayList<>();
        List<UserToService> userToServiceList = userToService.findAllUsersByService(service.getId());
        for (UserToService us : userToServiceList) {
            try {
                users.add((userService.findUserById(us.getUserId())).orElse(null));
            } catch (RuntimeException ex) {
                logger.error("There are no users here!", ex);
            }
        }
        return users;
    }

    public Service findServiceById(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            Service service = serviceDao.findById(serviceId).orElse(null);
            try {
                List<User> users = getSpecialists(Objects.requireNonNull(service));
                service.setUsers(users);
            } catch (NullPointerException e) {
                logger.error("There are no services here!", e);
            }
            return service;
        }
    }

    public List<Service> ascByPriceService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.ascByPriceService();
            for (Service service : services) {
                List<User> users = getSpecialists(service);
                service.setUsers(users);
            }
            return services;
        }
    }

    public List<Service> descByPriceService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.descByPriceService();
            for (Service service : services) {
                List<User> users = getSpecialists(service);
                service.setUsers(users);
            }
            return services;
        }
    }

    public List<Service> ascByTitleService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.ascByTitleService();
            for (Service service : services) {
                List<User> users = getSpecialists(service);
                service.setUsers(users);
            }
            return services;
        }
    }

    public List<Service> descByTitleService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.descByTitleService();
            for (Service service : services) {
                List<User> users = getSpecialists(service);
                service.setUsers(users);
            }
            return services;
        }
    }

    public List<String> getUniqueServiceTypes(List<Service> serviceList) {
        List<String> list = new ArrayList<>();
        for (Service service : serviceList) {
            list.add(service.getDescription());
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    public List<Service> filterByServiceType(String type) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.filterByServiceDescription(type);
            for (Service service : services) {
                List<User> users = getSpecialists(service);
                service.setUsers(users);
            }
            return services;
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
            if (orderToServiceService.isServiceExistsInOrder(serviceId, connection)) {
                errors.add(SERVICE_IS_IN_ORDER);
                return errors;
            }
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            serviceDao.delete(serviceId);
            userToService.deleteServiceToUser(serviceId, connection);
            orderToServiceService.deleteServiceToOrder(serviceId, connection);
            connection.commit();
        }
        return errors;
    }

    public Optional<Service> findServiceForOtherEntity(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.findById(id);
        }
    }

    public List<Service> findAll(int limit, int offset) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.findAll(limit, offset);
            for (Service service : services) {
                List<User> specialists = getSpecialists(service);
                service.setUsers(specialists);
            }
            return services;
        }
    }

    public int getNumberOfRows() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.getNumberOfRows();
        }
    }
}
