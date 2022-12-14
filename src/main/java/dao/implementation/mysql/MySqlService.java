package dao.implementation.mysql;

import dao.abstraction.ServiceDao;
import dao.implementation.mysql.converter.DtoConverter;
import dao.implementation.mysql.converter.ServiceDtoConverter;
import entity.Service;

import java.math.BigDecimal;
import java.sql.Connection;

import java.util.List;
import java.util.Optional;

public class MySqlService implements ServiceDao {

    private static final String SELECT_ALL =
            "SELECT service.id AS service_id , service.title AS service_title, " +
                    "service.description AS service_description, service.price AS " +
                    "service_price, service.image, service.duration " +
                    "FROM service ";

    private static final String WHERE_ID =
            "WHERE service.id = ? ";

    private static final String WHERE_TITLE =
            "WHERE service.title = ?";

    private static final String PAGINATION =
            "limit ? offset ? ";

    private static final String GROUP_BY =
            "GROUP BY service.id ";

    private static final String ASC_BY_PRICE =
            "ORDER BY service_price ASC ";

    private static final String DESC_BY_PRICE =
            "ORDER BY service_price DESC ";

    private static final String ASC_BY_TITLE =
            "ORDER BY service_title ASC ";

    private static final String DESC_BY_TITLE =
            "ORDER BY service_title DESC ";

    private static final String WHERE_SERVICE_DESCRIPTION =
            "WHERE service.description = ? ";

    private static final String INSERT =
            "INSERT into service (title, description, price, image, duration)" +
                    "VALUES(?, ?, ?, ?, ?) ";

    private static final String UPDATE =
            "UPDATE service SET title = ?, description = ?, price = ?, image = ?, duration = ? ";

    private static final String CHANGE_PRICE =
            "UPDATE service SET price =  ? ";

    private static final String DELETE =
            "DELETE FROM service ";

    private static final String NUMBER_OF_ROWS =
            "SELECT COUNT(*) FROM service";

    private final DefaultDaoImpl<Service> defaultDao;

    public MySqlService(Connection connection) {
        this(connection, new ServiceDtoConverter());
    }

    public MySqlService(Connection connection, DtoConverter<Service> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<Service> findById(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public Optional<Service> findByTitle(String title) {
        return defaultDao.findOne(SELECT_ALL + WHERE_TITLE, title);
    }

    @Override
    public List<Service> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public Service insert(Service service) {
        long id = defaultDao.executeInsertWithGeneratedPrimaryKey(INSERT,
                service.getTitle(), service.getDescription(), service.getPrice(),
                service.getImage(), service.getDuration());
        service.setId(id);
        return service;
    }

    @Override
    public void update(Service service) {
        defaultDao.executeUpdate(UPDATE + WHERE_ID, service.getTitle(),
                service.getDescription(), service.getPrice(), service.getImage(),
                service.getId());
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(DELETE + WHERE_ID, id);
    }

    @Override
    public void changePrice(Service service, BigDecimal price) {
        defaultDao.executeUpdate(CHANGE_PRICE + WHERE_ID, price,
                service.getId());
    }

    @Override
    public List<Service> filterByServiceDescription(String description) {
        return defaultDao.findAll(SELECT_ALL + WHERE_SERVICE_DESCRIPTION +
                GROUP_BY, description);
    }

    @Override
    public List<Service> ascByPriceService() {
        return defaultDao.findAll(SELECT_ALL + ASC_BY_PRICE);
    }

    @Override
    public List<Service> descByPriceService() {
        return defaultDao.findAll(SELECT_ALL + DESC_BY_PRICE);
    }

    @Override
    public List<Service> ascByTitleService() {
        return defaultDao.findAll(SELECT_ALL + ASC_BY_TITLE);
    }

    @Override
    public List<Service> descByTitleService() {
        return defaultDao.findAll(SELECT_ALL + DESC_BY_TITLE);
    }

    @Override
    public int getNumberOfRows() {
        return defaultDao.getNumberOfRows(NUMBER_OF_ROWS);
    }

    @Override
    public List<Service> findAll(int limit, int offset) {
        return defaultDao.findAll(SELECT_ALL + PAGINATION, limit, offset);
    }
}
