package dao.abstraction;

import entity.OrderToService;

import java.util.List;

public interface OrderToServiceDao extends GenericDao<OrderToService, Long> {

    /**
     * Retrieve objects from database identified by id.
     *
     * @param orderId identifier of order
     * @return list, which contains retrieved object
     */
    List<OrderToService> findAllByOrder(long orderId);

    /**
     * Retrieve objects from database identified by id.
     *
     * @param serviceId identifier of service
     * @return list, which contains retrieved object
     */
    List<OrderToService> findAllByService(long serviceId);

    boolean isServiceExistInBookedOrder(long serviceId);

    void deleteService(long id);
}
