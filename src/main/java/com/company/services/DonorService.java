package com.company.services;

import com.company.domain.Donor;
import com.company.domain.Donor;
import com.company.services.DAO.DonorDAO;

import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DonorService extends AbstractService {
    private static DonorDAO dao = new DonorDAO();

    public DonorService() {
        this.emf = Persistence.createEntityManagerFactory("JPA01");
    }

    public Donor getById(Long id) {
        final AtomicReference<Donor> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getById(id, em)));
        return td.get();
    }

    public Donor create(Donor Donor) {
        final AtomicReference<Donor> DonorId = new AtomicReference<>();
        doWork(em -> DonorId.set(dao.create(Donor, em)));
        return DonorId.get();
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

    public List<Donor> getDonors(int limit, int offset) {
        final AtomicReference<List<Donor>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.listAll(Donor.class, em)));
        return td.get();
    }
}

