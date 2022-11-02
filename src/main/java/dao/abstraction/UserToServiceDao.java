package dao.abstraction;

import entity.UserToService;

import java.util.List;

public interface UserToServiceDao extends GenericDao<UserToService, Long> {

    /**
     * Retrieve objects from database identified by id.
     *
     * @param userId identifier of user
     * @return list, which contains retrieved objects
     */
    List<UserToService> findAllByUser(long userId);

    /**
     * Retrieve objects from database identified by id.
     *
     * @param serviceId identifier of service
     * @return list, which contains retrieved objects
     */
    List<UserToService> findAllByService(long serviceId);

    void deleteService(long id);
}
