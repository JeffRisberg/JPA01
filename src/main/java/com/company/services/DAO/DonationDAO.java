package com.company.services.DAO;

import com.company.domain.Charity;
import com.company.domain.Donation;
import lombok.NonNull;

import javax.persistence.EntityManager;

public class DonationDAO extends BaseTemplateDAOImpl<Donation> {

    public DonationDAO() {
        super(Donation.class);
    }

    public Donation create(Donation obj, @NonNull EntityManager em) {
        return super.create(obj, em);
    }

    public Donation getById(Long id, @NonNull EntityManager em) {
        return super.getById(id, em);
    }

    public Boolean delete(Long id, @NonNull EntityManager em) {
        return super.deleteById(id, em);
    }
}