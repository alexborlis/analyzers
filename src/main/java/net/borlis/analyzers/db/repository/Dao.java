package net.borlis.analyzers.db.repository;

import net.borlis.analyzers.db.model.DBModel;
import org.hibernate.dialect.DB2390Dialect;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 06.10.15.
 */
public interface Dao<T extends DBModel, PK extends Serializable> {

    T findById(PK id);

    void save(T object);

    void delete(T object);

    Collection<T> findAll();

    T update(T object);

}
