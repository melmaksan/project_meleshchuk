package dao.abstraction;

import entity.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceDao extends GenericDao<Service, Long> {

    /**
     * Increase price of certain amount.
     *
     * @param service service to increase
     * @param price   price of increasing
     */
    void changeCost(Service service, BigDecimal price);

    /**
     * Retrieve services from database identified by name.
     *
     * @param title identifier of service
     * @return list, which contains services with certain name
     */
    List<Service> findByService(String title);

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

    int getNumberOfRows();
}
