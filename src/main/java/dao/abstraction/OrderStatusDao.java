package dao.abstraction;

import entity.OrderStatus;

import java.util.Optional;

public interface OrderStatusDao extends GenericDao<OrderStatus, Integer> {

    /**
     * Retrieve status from database identified by name.
     *
     * @param name identifier of status
     * @return optional, which contains retrieved object or null
     */
    Optional<OrderStatus> findByName(String name);
}
