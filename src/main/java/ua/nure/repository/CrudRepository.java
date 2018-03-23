package ua.nure.repository;

import java.util.List;

public interface CrudRepository<T> {

    void save(T t);

    T findOne(Integer id);

    List<T> findAll();

    void delete(Integer id);
}