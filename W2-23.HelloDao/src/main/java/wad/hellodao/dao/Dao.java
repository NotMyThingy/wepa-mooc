package wad.hellodao.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {

    T create(T t) throws SQLException;

    T findOne(K key) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(K key, T t) throws SQLException;

    void delete(K key) throws SQLException;

}
