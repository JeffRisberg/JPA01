package com.company.services;

import com.company.common.FilterDescription;
import com.company.common.ValueReference;
import com.company.services.DAO.BaseTemplateDAO;
import com.company.services.DAO.BaseTemplateDAOImpl;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

public class BaseController<T> {
    protected final DBSessionManager sessionManager;

    private final BaseTemplateDAO<T> dao;

    public BaseController(Configuration configuration, boolean isTenantDB, Class<T> daoClass) {
        this.sessionManager = DBSessionManager.getInstance(configuration, isTenantDB);
        this.dao = new BaseTemplateDAOImpl<>(daoClass);
    }

    public List<T> listAll(String namespace, Class<T> type) {
        final ValueReference<List<T>> ref = new ValueReference<>();
        this.sessionManager.useSession(namespace, session -> ref.set(this.dao.listAll(type, 0, 0, session)));
        return ref.get();
    }

    public T getById(String namespace, Serializable id) {
        final ValueReference<T> ref = new ValueReference<>();
        this.sessionManager.useSession(namespace, session -> ref.set(this.dao.getById(id, session)));
        return ref.get();
    }

    public T getById(Serializable id) {
        return this.getById(null, id);
    }

    public T create(String namespace, T obj) {
        final ValueReference<T> ref = new ValueReference<>();
        this.sessionManager.useSession(namespace, session -> ref.set(this.dao.create(obj, session)));
        return ref.get();
    }

    public T create(T obj) {
        return create(null, obj);
    }

    public boolean update(String namespace, T updatedEntity) {
        final ValueReference<Boolean> updated = new ValueReference<>();
        this.sessionManager.useSession(namespace, session -> updated.set(this.dao.update(updatedEntity, session)));
        return updated.get().booleanValue();
    }

    public boolean update(T updatedEntity) {
        return this.update(null, updatedEntity);
    }

    public boolean delete(String namespace, Serializable id) {
        final ValueReference<Boolean> deleted = new ValueReference<>();
        this.sessionManager.useSession(namespace, session -> deleted.set(this.dao.deleteById(id, session)));
        return deleted.get().booleanValue();
    }

    public boolean delete(Serializable id) {
        return this.delete(null, id);
    }

    public List<T> getByCriteria(String namespace, List<FilterDescription> filterDescriptions) {
        final ValueReference<List<T>> ref = new ValueReference<>();
        this.sessionManager.useSession(namespace, session -> ref.set(this.dao.getByCriteria(filterDescriptions, 0, 0, session)));
        return ref.get();
    }

    public List<T> getByCriteria(List<FilterDescription> filterDescriptions) {
        return getByCriteria(null, filterDescriptions);
    }

    protected BaseTemplateDAO<T> getDao() {
        return this.dao;
    }
}
