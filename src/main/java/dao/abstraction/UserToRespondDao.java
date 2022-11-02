package dao.abstraction;

import entity.UserToRespond;

import java.util.List;

public interface UserToRespondDao extends GenericDao<UserToRespond, Long>{

    /**
     * Retrieve objects from database identified by id.
     *
     * @param userId identifier of user
     * @return list, which contains retrieved objects
     */
    List<UserToRespond> findAllByUser(long userId);

    /**
     * Retrieve objects from database identified by id.
     *
     * @param respondId identifier of order
     * @return list, which contains retrieved objects
     */
    List<UserToRespond> findAllByRespond(long respondId);

    List<UserToRespond> findSpecialistByRespond(long orderId);

    List<UserToRespond> findClientsByRespond(long orderId);
}
