package dao.abstraction;

import java.util.List;
import java.util.Optional;

/**
 * Common interface for all dao.
 *
 * @param <T>  represents type of specific object
 * @param <K> represents type of identifier
 */
public interface GenericDao<T, K> {
    /**
     * Retrieves object from database identified by id.
     *
     * @param id identifier of specific object.
     * @return optional, which contains retrieved object or null
     */
    Optional<T> findById(K id);

    /**
     * Retrieves all object data from database.
     *
     * @return List of objects which represent one row in database.
     */
    List<T> findAll();

    /**
     * Insert object to a database.
     *
     * @param obj object to insert
     * @return inserted object
     */
    T insert(T obj);

    /**
     * Update object's information in database.
     *
     * @param obj object to update
     */
    void update(T obj);

    /**
     * Delete certain object identified by id from database.
     *
     * @param id identifier of the object.
     */
    void delete(K id);

    /**
     * Check object's existing in database.
     *
     * @param id identifier of the object.
     * @return true if exists, false if not exist
     */
    default boolean exist(K id) {
        return findById(id).isPresent();
    }
}

