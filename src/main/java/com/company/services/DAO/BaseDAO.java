package com.company.services.DAO;

import lombok.NonNull;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public interface BaseDAO {
    <T> T create(T obj, Class<T> type, EntityManager em);

    <T> T getById(Class<T> type, long id, EntityManager em);

    <T> List<T> getBySQL(Class<T> type, @NonNull String sql, @NonNull Map<String, Object> params, EntityManager em);

    int updateBySQL(@NonNull String sql, @NonNull Map<String, Object> params, EntityManager em);

    <T> T getById(Class<T> type, String id, EntityManager em);

    <T> Boolean update(T obj, EntityManager em);

    <T> Boolean deleteById(Class<T> type, long id, EntityManager em);

    <T> Boolean delete(T obj, Class<T> type, EntityManager em);

    <T> List<T> listAll(Class<T> type, EntityManager em);
}
