package repositories;

/**
 * Abstract repository interface which defines basic CRUD operation which should be implemented by implementing classes
 * Created by ebajrami on 4/23/16.
 */
public interface Repository<T> {
    /**
     * Returns object requested by id
     *
     * @param id
     * @return instance of object or null if object specified by id is not found
     */
    T findById(int id);

    /**
     * Persists object
     *
     * @param object
     */
    void add(T object);

    /**
     * Updates object data
     *
     * @param object
     */
    void update(T object);

    /**
     * Deletes object from persistance storage
     *
     * @param object
     */
    void delete(T object);

    /**
     * Deletes object but uses ID of the object. If not found no expcetion is thrown.
     *
     * @param object
     */
    void deleteById(T object);

    /**
     * Returns all object found in storage
     *
     * @return
     */
    Iterable<T> findAll();
}
