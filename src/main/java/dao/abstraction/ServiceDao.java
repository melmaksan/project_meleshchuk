package dao.abstraction;

import entity.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ServiceDao extends GenericDao<Service, Long> {

    /**
     * Increase price of certain amount.
     *
     * @param service service to increase
     * @param price   price of increasing
     */
    void changePrice(Service service, BigDecimal price);

    /**
     * Retrieve services from database identified by name.
     *
     * @param title identifier of service
     * @return list, which contains services with certain name
     */
    List<Service> filterByServiceDescription(String title);

    /**
     * Retrieve services from database sorted by price.
     *
     * @return list, which contains sorted services
     */
    List<Service> ascByPriceService();

    /**
     * Retrieve services from database sorted by price.
     *
     * @return list, which contains sorted services
     */
    List<Service> descByPriceService();

    /**
     * Retrieves object data from database.
     *
     * @return list of objects which represent one row in database.
     */
    List<Service> findAll(int limit, int offset);

    List<Service> ascByTitleService();

    List<Service> descByTitleService();

    int getNumberOfRows();

    Optional<Service> findByTitle(String title);

}
