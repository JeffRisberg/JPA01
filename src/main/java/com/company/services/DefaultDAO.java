package com.company.services;

import com.company.common.FilterDescription;
import lombok.NonNull;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DefaultDAO {

    Object create(Object obj, @NonNull Session session);

    Object getById(Serializable id, @NonNull Session session);

    List getBySQL(@NonNull String sql, @NonNull Map<String, Object> params, @NonNull Session session);

    List getByCriteria(List<FilterDescription> filterDescriptions, @NonNull Session session);

    List getByCriteria(@NonNull Map<String, Object> params, Session session);

    int updateBySQL(@NonNull String sql, @NonNull Map<String, Object> params, @NonNull Session session);

    Object getById(String id, @NonNull Session session);

    Object getByName(String name, @NonNull Session session);

    Boolean update(Object obj, @NonNull Session session);

    Boolean deleteById(Serializable id, @NonNull Session session);

    Boolean deleteById(String id, @NonNull Session session);

    Boolean delete(Object obj, @NonNull Session session);

    List listAll(@NonNull Session externalSession);
}

