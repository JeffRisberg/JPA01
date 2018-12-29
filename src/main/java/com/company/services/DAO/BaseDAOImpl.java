package com.company.services.DAO;

import com.company.common.FilterDesc;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class BaseDAOImpl implements BaseDAO {

    @Override
    public <T> T create(T obj, Class<T> type, EntityManager em) {
        em.persist(obj);
        return obj;
    }

    @Override
    public <T> T getById(Class<T> type, long id, EntityManager em) {
        AtomicReference<T> reference = new AtomicReference<>();
        reference.set(em.find(type, id));
        return reference.get();
    }

    @Override
    public <T> T getById(Class<T> type, String id, EntityManager em) {
        AtomicReference<T> reference = new AtomicReference<>();
        reference.set(em.find(type, id));
        return reference.get();
    }

    @Override
    public <T> List<T> getAll(Class<T> type, int limit, int offset, EntityManager em) {
        try {
            CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(type);
            criteria.select(criteria.from(type));
            Query query = em.createQuery(criteria);
            if (limit > 0) query.setMaxResults(limit);
            if (offset > 0) query.setFirstResult(offset);
            List<T> objects = query.getResultList();
            return objects;
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    /**
     * Execute Custom SQL with Map of parameters
     *
     * @param type
     * @param sql
     * @param params
     * @param em
     * @param <T>
     * @return Result
     */
    public <T> List<T> getBySQL(Class<T> type, @NonNull String
            sql, @NonNull Map<String, Object> params, EntityManager em) {

        try {
            Query query = em.createNativeQuery(sql);
            params.entrySet().stream().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
            List<T> result = query.getResultList();

            if (CollectionUtils.isEmpty(result))
                return Collections.emptyList();

            return result;
        } catch (Exception e) {
            log.error("getBySQL:: exception", e);
            return null;
        }
    }

    /**
     * Execute Custom SQL with Map of parameters
     *
     * @param sql
     * @param params
     * @param em
     * @return Result
     */
    public int updateBySQL(@NonNull String sql, @NonNull Map<String, Object> params, EntityManager em) {
        try {
            Query query = em.createNativeQuery(sql);
            params.entrySet().stream().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
            int updated = query.executeUpdate();
            return updated;
        } catch (Exception e) {
            log.error("updateBySQL:: exception", e);
            return 0;
        }
    }

    /**
     * Use Criteria Builder to execute a SQL By Parameters.
     * <p>
     * @param type
     * @param filterDescriptions
     * @param em
     * @param <T>
     * @return List of results
     */
    public <T> List<T> getByCriteria
    (Class<T> type, List<FilterDesc> filterDescriptions, int limit, int offset, @NonNull EntityManager em) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);

        Predicate p = criteriaBuilder.conjunction();

        for (FilterDesc fd : filterDescriptions) {
            String key = fd.getField();
            Object value = fd.getValue();

            if (value == null)
                continue;

            switch (fd.getOperator()) {
                case eq:
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get(key), value));
                    break;
                case like:
                    p = criteriaBuilder.and(p, criteriaBuilder.like(root.get(key), "%" + (String) value + "%"));
                    break;
                case gt:
                    p = criteriaBuilder.and(p, criteriaBuilder.greaterThan(root.get(key), (Comparable) value));
                    break;
                case lt:
                    p = criteriaBuilder.and(p, criteriaBuilder.lessThan(root.get(key), (Comparable) value));
                    break;
                case gte:
                    p = criteriaBuilder.and(p, criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Comparable) value));
                    break;
                case lte:
                    p = criteriaBuilder.and(p, criteriaBuilder.lessThanOrEqualTo(root.get(key), (Comparable) value));
                    break;
                case timestamp_gte:
                    p = criteriaBuilder.and(p, criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Timestamp) value));
                    break;
                case timestamp_lte:
                    p = criteriaBuilder.and(p, criteriaBuilder.lessThanOrEqualTo(root.get(key), (Timestamp) value));
                    break;
            }
        }
        criteriaQuery.where(p);

        Query query = em.createQuery(criteriaQuery);
        if (limit > 0) query.setMaxResults(limit);
        if (offset > 0) query.setFirstResult(offset);

        return query.getResultList();
    }

    @Override
    public <T> Boolean update(T obj, EntityManager em) {
        em.merge(obj);
        return true;
    }

    @Override
    public <T> Boolean deleteById(Class<T> type, long id, EntityManager em) {
        if (id != 0) {
            T obj = getById(type, id, em);
            if (obj != null) {
                em.remove(obj);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public <T> Boolean delete(T obj, Class<T> type, EntityManager em) {
        if (obj != null) {
            em.remove(obj);
            return true;
        } else {
            return false;
        }
    }
}
