package dao.abstraction;

import entity.PaymentStatus;

import java.util.Optional;

public interface PaymentStatusDao extends GenericDao<PaymentStatus, Integer> {

    /**
     * Retrieve status from database identified by name.
     *
     * @param name identifier of status
     * @return optional, which contains retrieved object or null
     */
    Optional<PaymentStatus> findByName(String name);
}
