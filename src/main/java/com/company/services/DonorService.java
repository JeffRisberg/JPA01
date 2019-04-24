package com.company.services;

import com.company.common.FilterDescription;
import com.company.domain.Charity;
import com.company.domain.Donor;
import com.company.services.DAO.DonorDAO;

import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DonorService extends AbstractService<Donor> {
    private static DonorDAO dao = new DonorDAO();

    public DonorService() {
        this.emf = Persistence.createEntityManagerFactory("JPA01");
    }

    public Donor create(Donor Donor) {
        final AtomicReference<Donor> created = new AtomicReference<>();
        doWork(em -> created.set(dao.create(Donor, em)));
        return created.get();
    }

    public Donor getById(Long id) {
        final AtomicReference<Donor> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getById(id, em)));
        return td.get();
    }

    public List<Donor> getAll(int limit, int offset) {
        final AtomicReference<List<Donor>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.listAll(Donor.class, limit, offset, em)));
        return td.get();
    }

    public List<Donor> getByCriteria(List<FilterDescription> filterDescriptions, int limit, int offset) {
        final AtomicReference<List<Donor>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getByCriteria(filterDescriptions, limit, offset, em)));
        return td.get();
    }

    public boolean update(Donor updatedEntity) {
        final AtomicReference<Boolean> updated = new AtomicReference<>();
        doWork(em -> updated.set(dao.update(updatedEntity, em)));
        return updated.get();
    }

    public boolean delete(Long id) {
        final AtomicReference<Boolean> deleted = new AtomicReference<>();
        doWork(em -> deleted.set(dao.delete(id, em)));
        return deleted.get();
    }

    public Donor getByName(String name) {
        final AtomicReference<Donor> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getByName(name, em)));
        return td.get();
    }
}

