package com.company.services.DAO;

import com.company.domain.Donation;
import lombok.NonNull;

import javax.persistence.EntityManager;

public class DonationDAO extends BaseDAOImpl {

    @Override
    public <T> T create(T obj, Class<T> type, EntityManager em) {
        return super.create(obj, type, em);
    }

    public Donation create(Donation obj, @NonNull EntityManager em) {
        return super.create(obj, Donation.class, em);
    }

    public Donation getById(Long id, @NonNull EntityManager em) {
        return super.getById(Donation.class, id, em);
    }

    public Boolean delete(Long id, @NonNull EntityManager em) {
        return super.deleteById(Donation.class, id, em);
    }
}

