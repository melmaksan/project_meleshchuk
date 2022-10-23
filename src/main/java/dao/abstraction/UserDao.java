package dao.abstraction;

import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    /**
     * Retrieve user from database identified by login.
     *
     * @param login identifier of user
     * @return optional, which contains retrieved object or null
     */
    Optional<User> findByLogin(String login);

    /**
     * Retrieve user from database identified by name.
     *
     * @param firstName firstName of user
     * @param lastName  lastName of user
     * @return optional, which contains retrieved object or null
     */
    Optional<User> findByName(String firstName, String lastName);

    /**
     * Retrieves object data from database.
     *
     * @return list of objects which represent one row in database.
     */
    List<User> findAll(int limit, int offset);

    /**
     * Retrieve services from database sorted by price.
     *
     * @return list, which contains sorted services
     */
    List<User> ascByRating();

    /**
     * Retrieve services from database sorted by price.
     *
     * @return list, which contains sorted services
     */
    List<User> descByRating();

    List<User> findAllSpecialists();

    List<User> findAllUsers();

    List<User> findAllAdmins();

    void changePassword(User user, String password);

    void updateRating(User user, float rate);

    List<User> ascByName();

    List<User> descByName();

    int getNumberOfRows();
}
