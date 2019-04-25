package com.company.services;

import com.company.common.FilterDescription;
import com.company.common.FilterOperator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class DefaultDAOImpl implements DefaultDAO {

  private Class type;

  public DefaultDAOImpl(Class clazz) {
    type = clazz;
  }

  @Override
  public Object create(Object obj, @NonNull Session session) {
    Serializable id = session.save(obj);
    obj = getById(id, session);
    return obj;
  }

  @Override
  public Object getById(Serializable id, @NonNull Session session) {
    AtomicReference<Object> reference = new AtomicReference<>();
    reference.set(session.get(type, id));
    return reference.get();
  }

  @Override
  public Object getById(String id, @NonNull Session session) {
    AtomicReference<Object> reference = new AtomicReference<>();
    reference.set(session.get(type, id));
    return reference.get();
  }

  @Override
  public List listAll(@NonNull Session session) {
    Query query;
    query = session.createQuery("from " + type.getName());
    List objects = query.list();
    return objects;
  }

  /**
   * Execute Custom SQL with Map of parameters
   */
  @Override
  public List getBySQL(@NonNull String sql, @NonNull Map<String, Object> params, @NonNull Session session) {
    try {
      Query query = session.createNativeQuery(
        sql)
        .addEntity(type);
      params.entrySet().stream().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
      List result = query.list();

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
   */
  @Override
  public int updateBySQL(@NonNull String sql, @NonNull Map<String, Object> params, @NonNull Session session) {
    try {
      Query query = session.createNativeQuery(sql);
      params.entrySet().stream().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
      int updated = query.executeUpdate();
      log.info("Updated/Deleted by SQL: {}", updated);
      return updated;
    } catch (Exception e) {
      log.error("updateBySQL:: exception", e);
      return 0;
    }
  }

  /**
   * Use Criteria Builder to execute a SQL By Parameters.
   * @return List of results
   */
  @Override
  public List getByCriteria(List<FilterDescription> filterDescriptions, @NonNull Session session) {
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(type);
    Root root = criteriaQuery.from(type);
    criteriaQuery.select(root);
    Predicate p = criteriaBuilder.conjunction();
    for (FilterDescription fd : filterDescriptions) {
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
    return session.createQuery(criteriaQuery).getResultList();
  }

  /**
   * Use Criteria Builder to execute a SQL By Parameters.
   *
   * @param params
   * @param session
   * @return List of results
   */
  @Override
  public List getByCriteria(@NonNull Map<String, Object> params, Session session) {
    try {
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(type);
      Root root = criteriaQuery.from(type);
      criteriaQuery.select(root);
      AtomicReference<Predicate> p = new AtomicReference<>(criteriaBuilder.conjunction());
      params.entrySet().stream().forEach(entry -> {
        p.set(criteriaBuilder.and(p.get(), criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue())));
      });
      criteriaQuery.where(p.get());
      return session.createQuery(criteriaQuery).getResultList();
    } catch (Exception e) {
      log.error("getByCriteria:: exception", e);
      return null;
    }
  }

  @Override
  public Boolean update(Object obj, @NonNull Session session) {
    session.update(obj);
    return true;
  }

  @Override
  public Boolean deleteById(Serializable id, @NonNull Session session) {
    if (id != null) {
      log.info("Deleting object with Id {}", id);
      Object obj = getById(id, session);
      if (obj != null) {
        session.delete(obj);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public Object getByName(String name, @NonNull Session session) {
    final AtomicReference<Object> ref = new AtomicReference<>();
    List<FilterDescription> descriptions = new ArrayList<>();
    descriptions.add(new FilterDescription("name", FilterOperator.eq, name));
    List list = getByCriteria(descriptions, session);
    if (list.isEmpty()) {
      ref.set(null);
    } else {
      ref.set(list.get(0));
    }
    return ref.get();
  }

  @Override
  public Boolean deleteById(String id, @NonNull Session session) {
    if (id != null) {
      log.info("Deleting object with Id: {}", id);
      Object obj = getById(id, session);
      if (obj != null) {
        session.delete(obj);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public Boolean delete(Object obj, @NonNull Session session) {
    if (obj != null) {
      log.info("Deleting object");
      session.delete(obj);
      return true;
    } else {
      return false;
    }
  }
}
