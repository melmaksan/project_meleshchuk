package dao.abstraction;

import entity.UserToOrder;
import entity.UserToService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserToOrderDao extends GenericDao<UserToOrder, Long> {

    /**
     * Retrieve objects from database identified by id.
     *
     * @param userId identifier of user
     * @return list, which contains retrieved objects
     */
    List<UserToOrder> findAllByUser(long userId);

    /**
     * Retrieve objects from database identified by id.
     *
     * @param orderId identifier of order
     * @return list, which contains retrieved objects
     */
    List<UserToOrder> findAllByOrder(long orderId);

    List<UserToOrder> findSpecialistsByOrder(long orderId);

    List<UserToOrder> findClientsByOrder(long orderId);

    List<UserToOrder> findAllBySpec(long userId, LocalDate dateFrom, LocalDate dateTo, int status);

    boolean isSpecExistsInOrder(long userId);
}
