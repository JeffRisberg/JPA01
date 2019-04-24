package com.company.services;

import com.company.common.FilterDescription;
import com.company.domain.Charity;
import com.company.services.DAO.CharityDAO;

import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CharityService extends AbstractService<Charity> {
    private static CharityDAO dao = new CharityDAO();

    public CharityService() {
        this.emf = Persistence.createEntityManagerFactory("JPA01");
    }

    public Charity create(Charity Charity) {
        final AtomicReference<Charity> created = new AtomicReference<>();
        doWork(em -> created.set(dao.create(Charity, em)));
        return created.get();
    }

    public Charity getById(Long id) {
        final AtomicReference<Charity> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getById(id, em)));
        return td.get();
    }

    public List<Charity> getAll(int limit, int offset) {
        final AtomicReference<List<Charity>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.listAll(Charity.class, limit, offset, em)));
        return td.get();
    }

    public List<Charity> getByCriteria(List<FilterDescription> filterDescriptions, int limit, int offset) {
        final AtomicReference<List<Charity>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getByCriteria(filterDescriptions, limit, offset, em)));
        return td.get();
    }

    public boolean update(Charity updatedEntity) {
        final AtomicReference<Boolean> updated = new AtomicReference<>();
        doWork(em -> updated.set(dao.update(updatedEntity, em)));
        return updated.get();
    }

    public boolean delete(Long id) {
        final AtomicReference<Boolean> deleted = new AtomicReference<>();
        doWork(em -> deleted.set(dao.delete(id, em)));
        return deleted.get();
    }

    public Charity getByName(String name) {
        final AtomicReference<Charity> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getByName(name, em)));
        return td.get();
    }
}

