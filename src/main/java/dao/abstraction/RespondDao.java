package dao.abstraction;

import entity.Respond;

import java.util.List;

public interface RespondDao extends GenericDao<Respond, Long> {

    /**
     * Retrieve respond from database identified by user.
     *
     * @param userId identifier of includedPackage
     * @return list, which contains response of certain user
     */
    List<Respond> findByUser(long userId);
}
