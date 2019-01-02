package com.company.services;

import com.company.common.FilterDesc;
import com.company.domain.Donation;
import com.company.domain.Donor;
import com.company.services.DAO.DonationDAO;

import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DonationService extends AbstractService<Donation> {
    private static DonationDAO dao = new DonationDAO();

    public DonationService() {
        this.emf = Persistence.createEntityManagerFactory("JPA01");
    }

    public Donation create(Donation Donation) {
        final AtomicReference<Donation> created = new AtomicReference<>();
        doWork(em -> created.set(dao.create(Donation, em)));
        return created.get();
    }

    public Donation getById(Long id) {
        final AtomicReference<Donation> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getById(id, em)));
        return td.get();
    }

    public List<Donation> getAll(int limit, int offset) {
        final AtomicReference<List<Donation>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getAll(Donation.class, limit, offset, em)));
        return td.get();
    }

    public List<Donation> getByCriteria(List<FilterDesc> filterDescriptions, int limit, int offset) {
        final AtomicReference<List<Donation>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getByCriteria(Donation.class, filterDescriptions, limit, offset, em)));
        return td.get();
    }

    public boolean update(Donation updatedEntity) {
        final AtomicReference<Boolean> updated = new AtomicReference<>();
        doWork(em -> updated.set(dao.update(updatedEntity, em)));
        return updated.get();
    }

    public boolean delete(Long id) {
        final AtomicReference<Boolean> deleted = new AtomicReference<>();
        doWork(em -> deleted.set(dao.delete(id, em)));
        return deleted.get();
    }
}

