package com.company.services;

import com.company.common.FilterDescription;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BaseController {
    protected final DBSessionManager sessionManager;

    private DefaultDAO dao;

    protected DefaultDAO getDao() {
        return dao;
    }

    public BaseController(Configuration configuration, boolean isTenantDB, Class forClass) {
        sessionManager = DBSessionManager.getInstance(configuration, isTenantDB);
        dao = new DefaultDAOImpl(forClass);
    }

    public List listAll(String namespace) {
        final AtomicReference<List> ref = new AtomicReference<>();
        this.sessionManager.useSession(namespace, session -> ref.set(getDao().listAll(session)));
        return ref.get();
    }

    public List listAll() {
        return listAll(null);
    }

    public Object getById(String namespace, Serializable id) {
        final AtomicReference ref = new AtomicReference();
        this.sessionManager.useSession(namespace, session -> ref.set(getDao().getById(id, session)));
        return ref.get();
    }

    public Object getById(Serializable id) {
        return getById(null, id);
    }

    public Object create(String namespace, Object obj) {
        final AtomicReference ref = new AtomicReference();
        this.sessionManager.useSession(namespace, session -> ref.set(getDao().create(obj, session)));
        return ref.get();
    }

    public Object create(Object obj) {
        return create(null, obj);
    }

    public boolean update(String namespace, Object updatedEntity) {
        final AtomicReference<Boolean> updated = new AtomicReference<>();
        this.sessionManager.useSession(namespace, session -> updated.set(getDao().update(updatedEntity, session)));
        return updated.get();
    }

    public boolean update(Object updatedEntity) {
        return update(null, updatedEntity);
    }

    public boolean delete(String namespace, Serializable id) {
        final AtomicReference<Boolean> deleted = new AtomicReference<>();
        this.sessionManager.useSession(namespace, session -> deleted.set(getDao().deleteById(id, session)));
        return deleted.get();
    }

    public boolean delete(Serializable id) {
        return delete(null, id);
    }

    public List getByCriteria(String namespace, List<FilterDescription> filterDescriptions) {
        final AtomicReference<List> ref = new AtomicReference<>();
        this.sessionManager.useSession(namespace, session -> ref.set(getDao().getByCriteria(filterDescriptions, session)));
        return ref.get();
    }

    public List getByCriteria(List<FilterDescription> filterDescriptions) {
        return getByCriteria(null, filterDescriptions);
    }
}
