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
import java.util.Objects;
import java.util.Optional;
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
                users.add((userService.findUserForOtherEntity(userToService.getUserId())).orElse(null));
            } catch (RuntimeException ex){
                logger.error("There are no users here!", ex);
            }
        }
        return users;
    }

    public Service findServiceById(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            Service service = null;
            try {
                service = serviceDao.findById(serviceId).orElseThrow(NoSuchFieldException::new);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            List<User> users = getUsers(Objects.requireNonNull(service));
            service.setUsers(users);
            return service;
        }
    }

    public List<Service> ascByPriceService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.ascByPriceService();
            for (Service service : services) {
                List<User> users = getUsers(service);
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
                List<User> users = getUsers(service);
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
                List<User> users = getUsers(service);
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
                List<User> users = getUsers(service);
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
                List<User> users = getUsers(service);
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
            connection.commit();
        }
        return errors;
    }

    public List<Service> findAll(int limit, int offset) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.findAll(limit, offset);
        }
    }

    public Optional<Service> findServiceForOtherEntity(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.findById(id);
        }
    }
}
