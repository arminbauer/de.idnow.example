package repositories;

/**
 * Created by ebajrami on 4/23/16.
 */
public interface Repository<T> {
    T findById(int id);

    void add(T object);

    void update(T object);

    void delete(T object);

    void deleteById(T object);

    Iterable<T> findAll();
}
