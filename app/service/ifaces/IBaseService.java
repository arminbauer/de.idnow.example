package service.ifaces;

import java.util.List;

/**
 * Base service class
 *
 * Created by sreenath on 15.07.16.
 */
public interface IBaseService<T> {

    T getById(String id);

    List getAll();

    void create(T obj);

    T update (T obj);

    T delete (T obj);
}
